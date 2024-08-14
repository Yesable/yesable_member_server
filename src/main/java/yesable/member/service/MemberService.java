package yesable.member.service;




import com.example.grpc.RegisterUserRequest;
import com.example.grpc.RegisterUserResponse;
import com.example.grpc.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import yesable.member.dto.CompanyUserDTO;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.mapper.MemberMapper;
import yesable.member.model.entity.mariadb.user.CompanyUser;
import yesable.member.model.entity.mariadb.user.CoreUser;
import yesable.member.model.entity.mariadb.user.PrivateUser;
import yesable.member.repository.mariadb.CompanyUserRepository;
import yesable.member.repository.mariadb.CoreUserRepository;
import yesable.member.repository.mariadb.PrivateUserRepository;

@GrpcService
public class MemberService extends UserServiceGrpc.UserServiceImplBase{

    private final CoreUserRepository coreUserRepository;
    private final PrivateUserRepository privateUserRepository;
    private final CompanyUserRepository companyUserRepository;
    private final MemberMapper memberMapper;

    public MemberService(CoreUserRepository coreUserRepository, PrivateUserRepository privateUserRepository, CompanyUserRepository companyUserRepository) {
        this.coreUserRepository = coreUserRepository;
        this.privateUserRepository = privateUserRepository;
        this.companyUserRepository = companyUserRepository;
        this.memberMapper = MemberMapper.INSTANCE;
    }

    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<RegisterUserResponse> responseobserver) {
        String message;
        boolean result=false;

        if(request.hasPrivateUser()) {
            PrivateUserDTO privateuserdto=memberMapper.grpcToDto(request.getPrivateUser());
            CoreUser coreuser=memberMapper.toEntity(privateuserdto);
            PrivateUser privateuser=memberMapper.dtoToEntity(privateuserdto);

            coreUserRepository.save(coreuser);
            privateUserRepository.save(privateuser);

            message= "Private User "+coreuser.getId()+" registered successfully";
            result=true;

        } else if (request.hasCompanyUser()) {
            CompanyUserDTO companyuserdto = memberMapper.grpcToDto(request.getCompanyUser());
            CoreUser coreuser=memberMapper.toEntity(companyuserdto);
            CompanyUser companyuser=memberMapper.dtoToEntity(companyuserdto);

            coreUserRepository.save(coreuser);
            companyUserRepository.save(companyuser);

            message= "Company User "+coreuser.getId()+" registered successfully";
            result=true;
        } else {
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


    //id, passwd가 오면 이 유저가 있는지 없는지, 있으면 id 없으면 null이나 error나
}
