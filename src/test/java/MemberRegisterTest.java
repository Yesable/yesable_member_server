import com.example.grpc.*;
import com.netflix.discovery.converters.Auto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import yesable.member.Main;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= Main.class)
public class MemberRegisterTest {


    @Autowired
    UserServiceGrpc.UserServiceBlockingStub stub;

    @Test
    public void RegisterTest() {
        CoreUserGRPC coreUser = CoreUserGRPC.newBuilder()
                .setId(214L)
                .setPassword("password123")
                .setEmail("john.doe@example.com")
                .setPhoneNumber("123-456-7890")
                .setName("John Doe")
                .setGender(CoreUserGRPC.Gender.MALE)
                .setDateOfBirth("1985-05-15")
                .build();

        PrivateUserGRPC privateUser = PrivateUserGRPC.newBuilder()
                .setCoreUser(coreUser)
                .setUsername("johnny")
                .setLocation("New York")
                .addInterestField(PrivateUserGRPC.InterestField.IT)
                .addInterestField(PrivateUserGRPC.InterestField.SALES)
                .addWorkType(PrivateUserGRPC.WorkType.INTERN)
                .addSkills("Java")
                .addSkills("Spring")
                .setEducationlevel(PrivateUserGRPC.Educationlevel.GOJOL)
                .setPersonality("Outgoing")
                .setHobbies("Gaming")
                .setSupportneeds("None")
                .setDisabilitytype(PrivateUserGRPC.Disabilitytype.MENTAL_DISABILITY_AUTISM_IMPAIRED)
                .build();

        RegisterUserRequest request = RegisterUserRequest.newBuilder()
                .setPrivateUser(privateUser)
                .build();

        RegisterUserResponse response = stub.registerUser(request);

        assertTrue(response.getSuccess(),"true가 나왔음");

    }
}
