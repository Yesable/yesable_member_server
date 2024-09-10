package yesable.member.service;




import com.example.grpc.*;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import yesable.member.dto.CompanyUserDTO;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.mapper.MemberMapper;
import yesable.member.model.entity.mariadb.user.CompanyUser;
import yesable.member.model.entity.mariadb.user.CoreUser;
import yesable.member.model.entity.mariadb.user.Experience;
import yesable.member.model.entity.mariadb.user.PrivateUser;
import yesable.member.repository.mariadb.CompanyUserRepository;
import yesable.member.repository.mariadb.CoreUserRepository;
import yesable.member.repository.mariadb.ExperienceRepository;

import yesable.member.repository.mariadb.PrivateUserRepository;

import javax.crypto.SecretKey;
import java.util.Optional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase{


    private final PrivateUserRepository privateUserRepository;
    private final CompanyUserRepository companyUserRepository;
    private final ExperienceRepository experienceRepository;

    private final MemberMapper memberMapper;

    public UserService( PrivateUserRepository privateUserRepository, CompanyUserRepository companyUserRepository, ExperienceRepository experienceRepository) {
        this.privateUserRepository = privateUserRepository;
        this.companyUserRepository=companyUserRepository;
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

        } else if(request.hasCompanyuser()){
            CompanyUserDTO companyuserdto=memberMapper.grpcToDto(request.getCompanyuser());
            CompanyUser companyuser=memberMapper.dtoToEntity(companyuserdto);

            companyUserRepository.save(companyuser);

            message = "companyUser registered successful";

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




    @Override
    public void onBoardingUser(OnboardingUserRequest request, StreamObserver<OnboardingUserResponse> response) {


    }





    //id, passwd가 오면 이 유저가 있는지 없는지, 있으면 id 없으면 null이나 error나
}
