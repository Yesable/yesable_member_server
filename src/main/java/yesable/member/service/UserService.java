package yesable.member.service;




import com.example.grpc.*;
import dev.paseto.jpaseto.Pasetos;
import dev.paseto.jpaseto.lang.Keys;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.mapper.MemberMapper;
import yesable.member.model.entity.mariadb.user.CoreUser;
import yesable.member.model.entity.mariadb.user.PrivateUser;
import yesable.member.repository.mariadb.CompanyUserRepository;
import yesable.member.repository.mariadb.CoreUserRepository;
import yesable.member.repository.mariadb.PrivateUserRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase{

    private final CoreUserRepository coreUserRepository;
    private final PrivateUserRepository privateUserRepository;
    private final CompanyUserRepository companyUserRepository;
    private final MemberMapper memberMapper;

    public UserService(CoreUserRepository coreUserRepository, PrivateUserRepository privateUserRepository, CompanyUserRepository companyUserRepository) {
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


    @Override
    public void loginUser(LoginUserRequest request, StreamObserver<LoginUserResponse> responseobserver) {
        boolean success = authenticateUser(request.getId(), request.getPassword());
        String token = "";

        if (success) {
            token = generateTokenForUser(request.getId()); // 토큰 생성 로직
        }

        LoginUserResponse response = LoginUserResponse.newBuilder()
                .setSuccess(success)
                .setToken(token)
                .build();

        responseobserver.onNext(response);
        responseobserver.onCompleted();


    }

    private String generateTokenForUser(String id) {
        SecretKey key= Keys.secretKey();
        String token = Pasetos.V1.LOCAL.builder()
                .setSubject("yesable")
                .setSharedSecret(key)
                .compact();

        return token;
    }

    private boolean authenticateUser(String id, String password) {
        Optional<CoreUser> userOpt = Optional.ofNullable(coreUserRepository.findByID(id));

        if (userOpt.isPresent()) {

            return true;
        }

        return false; // 사용자명에 해당하는 사용자가 없을 때
    }


    @Override
    public void logoutUser(LogoutUserRequest request, StreamObserver<LogoutUserResponse> responseObserver) {
        // 로그아웃 로직 구현
        boolean success = invalidateToken(request.getToken()); // 토큰 무효화 로직

        LogoutUserResponse response = LogoutUserResponse.newBuilder()
                .setSuccess(success)
                .setMessage(success ? "Logged out successfully" : "Logout failed")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private boolean invalidateToken(String token) {
        return true; //임시값

    }

    @Override
    public void onBoardingUser(OnboardingUserRequest request, StreamObserver<OnboardingUserResponse> response) {


    }


    //id, passwd가 오면 이 유저가 있는지 없는지, 있으면 id 없으면 null이나 error나
}
