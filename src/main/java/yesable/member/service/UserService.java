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
            String encodedPassword = passwordEncoder.encode(privateuserdto.getPassword());
            privateuserdto.setPassword(encodedPassword);  // 암호화된 비밀번호를 DTO에 설정

            //사용자 로그인 id 암호화
            String encodedId=passwordEncoder.encode(privateuserdto.getId());
            privateuserdto.setId(encodedId);


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
    public void getPrivateUser(GetPrivateUserRequest request, StreamObserver<GetPrivateUserResponse> responseobserver) {
        PrivateUser privateUserentity=privateUserRepository.findPrivateUserById(request.getUserId());

        if(privateUserentity==null) {
            responseobserver.onError(new StatusRuntimeException(Status.NOT_FOUND.withDescription("PrivateUser Not Found")));
        }
        PrivateUserDTO privateUserDTO=memberMapper.entitytoDto(privateUserentity);


        PrivateUserGRPC privateUserGRPC=memberMapper.dtoToGrpc(privateUserDTO);
        GetPrivateUserResponse response = GetPrivateUserResponse.newBuilder()
                .setPrivateUser(privateUserGRPC)
                .build();

        // 응답 전송
        responseobserver.onNext(response);
        responseobserver.onCompleted();
    }

    @Transactional
    @Override
    public void getPrivateUserId(GetPrivateUserIdRequest request, StreamObserver<GetPrivateUserIdResponse> responseobserver) {

    }
}