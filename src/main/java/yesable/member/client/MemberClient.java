
/*
package yesable.member.client;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import yesable.member.enums.user.Gender;


public class MemberClient { //백엔드 단에서만 사용하는 gRPC 테스트 클라이언트

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();


        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        // 예시: PrivateUser 등록
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

        System.out.println(response.getMessage());
        System.out.println("Success: " + response.getSuccess());

        channel.shutdown();
    }
}
*/