
/*
package yesable.member.client;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class MemberClient { //백엔드 단에서만 사용하는 gRPC 테스트 클라이언트

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        // 예시: PrivateUser 등록
        CoreUserGRPC coreUser = CoreUserGRPC.newBuilder()
                .setId("john_doe")
                .setPassword("password123")
                .setEmail("john.doe@example.com")
                .setPhoneNumber("123-456-7890")
                .setName("John Doe")
                .setGender("Male")
                .setDateOfBirth("1985-05-15")
                .build();

        PrivateUserGRPC privateUser = PrivateUserGRPC.newBuilder()
                .setCoreUser(coreUser)
                .setUsername("johnny")
                .setLocation("New York")
                .addInterestField("IT")
                .addInterestField("Music")
                .addWorkType("Full-Time")
                .addSkills("Java")
                .addSkills("Spring")
                .setEducation("Bachelor")
                .setPersonality("Outgoing")
                .setHobbies("Gaming")
                .setSupportneeds("None")
                .setDisabilitytype("None")
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