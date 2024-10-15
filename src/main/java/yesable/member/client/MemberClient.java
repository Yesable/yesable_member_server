
package yesable.member.client;

import com.example.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class MemberClient { //백엔드 단에서만 사용하는 gRPC 테스트 클라이언트

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("172.30.1.41", 9999)
                .usePlaintext()
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        // 예시: PrivateUser 등록

        LocalDateGRPC dateOfBirth = LocalDateGRPC.newBuilder()
                .setYear(1990)
                .setMonth(12)
                .setDay(25)
                .build();

        CoreUserGRPC coreUser = CoreUserGRPC.newBuilder()
                .setId("john_doe")
                .setPassword("password123")
                .setEmail("john.doe@example.com")
                .setPhoneNumber("123-456-7890")
                .setName("John Doe")
                .setGender(GenderGRPC.forNumber(1))
                .setDateOfBirth(dateOfBirth)
                .build();

        PrivateUserGRPC privateUser = PrivateUserGRPC.newBuilder()
                .setCoreUser(coreUser)
                .setUsername("johnny")
                .setLocation("New York")
                .addInterestField(InterestFieldGRPC.forNumber(1))
                .addInterestField(InterestFieldGRPC.forNumber(1))
                .addWorkType(WorkTypeGRPC.forNumber(1))
                .addSkills("Java")
                .addSkills("Spring")
                .setEducationlevel(EducationlevelGRPC.forNumber(1))
                .setPersonality("Outgoing")
                .setHobbies("Gaming")
                .setSupportneeds("None")
                .setDisabilitytype(DisabilitytypeGRPC.forNumber(1))
                .build();

        RegisterUserRequest request = RegisterUserRequest.newBuilder()
                .setPrivateuser(privateUser)
                .build();

        RegisterUserResponse response = stub.registerUser(request);

        System.out.println(response.getMessage());
        System.out.println("Success: " + response.getSuccess());

        channel.shutdown();
    }
}
