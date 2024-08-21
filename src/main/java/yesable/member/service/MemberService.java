package yesable.member.service;




import com.example.grpc.PrivateUserGRPC;
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
            String hoo = request.getPrivateUser().getHobbies();
            System.out.println(hoo+"\n\n\n");

            PrivateUserDTO privateuserdto=memberMapper.grpcToDto(request.getPrivateUser());
            System.out.println(privateuserdto.getHobbies()+"DTO \n\n\n");
            System.out.println(request.getPrivateUser().getCoreUser().getEmail()+" GRPC \n\n\n");
            PrivateUser privateuser=memberMapper.dtoToEntity(privateuserdto);


            privateUserRepository.save(privateuser);

            message = "Private User registered successfully with details:\n" +
                    "CoreUser:\n" +
                    "  ID: " + privateuser.getId() + "\n" +
                    "  Email: " + privateuser.getEmail() + "\n" +
                    "  Phone Number: " + privateuser.getPhoneNumber() + "\n" +
                    "  Name: " + privateuser.getName() + "\n" +
                    "  Gender: " + privateuser.getGender() + "\n" +
                    "  Date of Birth: " + privateuser.getDateOfBirth() + "\n" +
                    "PrivateUser:\n" +
                    "  Username: " + privateuser.getUsername() + "\n" +
                    "  Location: " + privateuser.getLocation() + "\n" +
                    "  Interests: " + privateuser.getInterestField() + "\n" +
                    "  Work Type: " + privateuser.getWorkType() + "\n" +
                    "  Skills: " + privateuser.getSkills() + "\n" +
                    "  Education Level: " + privateuser.getEducationlevel() + "\n" +
                    "  Personality: " + privateuser.getPersonality() + "\n" +
                    "  Hobbies: " + privateuser.getHobbies() + "\n" +
                    "  Support Needs: " + privateuser.getSupportneeds() + "\n" +
                    "  Disability Type: " + privateuser.getDisabilitytype();

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
