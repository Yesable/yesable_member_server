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

import yesable.member.repository.mariadb.PrivateUserRepository;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase{


    private final PrivateUserRepository privateUserRepository;
    private final ExperienceRepository experienceRepository;

    private final MemberMapper memberMapper;

    public UserService( PrivateUserRepository privateUserRepository,  ExperienceRepository experienceRepository) {
        this.privateUserRepository = privateUserRepository;
        this.experienceRepository=experienceRepository;
        this.memberMapper = MemberMapper.INSTANCE;
    }

    @Transactional
    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<RegisterUserResponse> responseobserver) {
        String message;
        boolean result;


        if(request.hasPrivateuser()) {
            PrivateUserDTO privateuserdto=memberMapper.grpcToDto(request.getPrivateuser());

            PrivateUser privateuser=memberMapper.dtoToEntity(privateuserdto);

            privateUserRepository.save(privateuser);


            for(Experience ex:privateuser.getExperiences()) {
                ex.setPrivateUser(privateuser);

                experienceRepository.save(ex); //experience를 소유한 privateUser 를 나타내기 위해 ID값이 필요한데, 따라서 우선 PrivateUser를 저장한 후 Ex를 저장

            }



            message = "Private User registered successful";



            result=true;

        }else {

            message = "register failed";
            result = false;
        }

        RegisterUserResponse response= RegisterUserResponse.newBuilder()
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
        System.out.println("sadfsadf : " +privateUserentity.getName()+"\n\n\n");

        if(privateUserentity==null) {
            responseobserver.onError(new StatusRuntimeException(Status.NOT_FOUND.withDescription("PrivateUser Not Found")));
        }
        PrivateUserDTO privateUserDTO=memberMapper.entitytoDto(privateUserentity);
        System.out.println("ㅁㄴㅇ: " +privateUserDTO.getName()+"asdf    " +privateUserDTO.getInterestField()+"\n\n\n");

        PrivateUserGRPC privateUserGRPC=memberMapper.dtoToGrpc(privateUserDTO);
        GetPrivateUserResponse response = GetPrivateUserResponse.newBuilder()
                .setPrivateUser(privateUserGRPC)
                .build();

        // 응답 전송
        responseobserver.onNext(response);
        responseobserver.onCompleted();
    }


}
