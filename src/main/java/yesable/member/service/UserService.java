package yesable.member.service;




import com.example.grpc.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.mapper.MemberMapper;
import yesable.member.model.entity.mariadb.user.Experience;
import yesable.member.model.entity.mariadb.user.PrivateUser;
import yesable.member.repository.mariadb.ExperienceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import yesable.member.repository.mariadb.PrivateUserRepository;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase{

    private final PrivateUserRepository privateUserRepository;
    private final ExperienceRepository experienceRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 추가

    public UserService(PrivateUserRepository privateUserRepository, ExperienceRepository experienceRepository, PasswordEncoder passwordEncoder) {
        this.privateUserRepository = privateUserRepository;
        this.experienceRepository = experienceRepository;
        this.passwordEncoder = passwordEncoder; // PasswordEncoder 주입
        this.memberMapper = MemberMapper.INSTANCE;
    }

    @Transactional
    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<RegisterUserResponse> responseobserver) {
        String message;
        boolean result;

        if (request.hasPrivateuser()) {
            // DTO로 변환
            PrivateUserDTO privateuserdto = memberMapper.grpcToDto(request.getPrivateuser());
            // 비밀번호 암호화
            //String encodedPassword = passwordEncoder.encode(privateuserdto.getPassword());
            privateuserdto.setPassword(privateuserdto.getPassword());  //  비밀번호를 DTO에 설정

            //사용자 로그인 id 암호화
          //  String encodedId=passwordEncoder.encode(privateuserdto.getId());
            privateuserdto.setId(privateuserdto.getId());


            // DTO를 엔티티로 변환
            PrivateUser privateuser = memberMapper.dtoToEntity(privateuserdto);

            // PrivateUser 저장
            privateUserRepository.save(privateuser);

            // Experience 저장
            for (Experience ex : privateuser.getExperiences()) {
                ex.setPrivateUser(privateuser);
                experienceRepository.save(ex);
            }

            message = "Private User registered successfully";
            result = true;
        } else {
            message = "Register failed";
            result = false;
        }

        RegisterUserResponse response = RegisterUserResponse.newBuilder()
                .setMessage(message)
                .setSuccess(result)
                .build();

        responseobserver.onNext(response);
        responseobserver.onCompleted();
    }


    @Transactional
    @Override
    public void getPrivateUserId(GetPrivateUserIdRequest request, StreamObserver<GetPrivateUserIdResponse> responseobserver) {
        PrivateUser privateUserentity=privateUserRepository.findPrivateUserById(request.getUserId());

        if(privateUserentity==null) {
            responseobserver.onError(new StatusRuntimeException(Status.NOT_FOUND.withDescription("PrivateUser Not Found")));
        }
        PrivateUserDTO privateUserDTO=memberMapper.entitytoDto(privateUserentity);


        PrivateUserGRPC privateUserGRPC=memberMapper.dtoToGrpc(privateUserDTO);
        GetPrivateUserIdResponse response = GetPrivateUserIdResponse.newBuilder()
                .setUserseq(privateUserDTO.getUserSeq())
                .build();

        // 응답 전송
        responseobserver.onNext(response);
        responseobserver.onCompleted();

    }


    @Transactional
    @Override
    public void updateUser(UpdateUserRequest request, StreamObserver<UpdateUserResponse> responseObserver) {
        String message;
        boolean result;

        if (request.hasPrivateuser()) {
            // DTO로 변환
            PrivateUserDTO privateUserDTO = memberMapper.grpcToDto(request.getPrivateuser());

            // 사용자 ID로 기존 사용자 검색
            PrivateUser existingUser = privateUserRepository.findPrivateUserById(privateUserDTO.getId());

            if (existingUser == null) {
                responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND.withDescription("User not found")));
                return;
            }

            // 변경된 정보만 업데이트
            // ID 변경 가능
            if (privateUserDTO.getId() != null && !privateUserDTO.getId().isEmpty()) {
                existingUser.setId(privateUserDTO.getId());
            }

            // 비밀번호가 있는 경우에만 암호화하여 업데이트
            if (privateUserDTO.getPassword() != null && !privateUserDTO.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(privateUserDTO.getPassword());
                existingUser.setPassword(encodedPassword);
            }

            // 다른 CoreUser 필드들 업데이트
            if (privateUserDTO.getName() != null) {
                existingUser.setName(privateUserDTO.getName());
            }
            if (privateUserDTO.getEmail() != null) {
                existingUser.setEmail(privateUserDTO.getEmail());
            }
            if (privateUserDTO.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(privateUserDTO.getPhoneNumber());
            }
            if (privateUserDTO.getGender() != null) {
                existingUser.setGender(privateUserDTO.getGender());
            }
            if (privateUserDTO.getDateOfBirth() != null) {
                existingUser.setDateOfBirth(privateUserDTO.getDateOfBirth());
            }

            // 변경된 사용자 정보를 저장
            privateUserRepository.save(existingUser);

            message = "User updated successfully";
            result = true;
        } else {
            message = "Update failed";
            result = false;
        }

        UpdateUserResponse response = UpdateUserResponse.newBuilder()
                .setMessage(message)
                .setSuccess(result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}