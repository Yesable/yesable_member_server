package yesable.member.dto;

import com.example.grpc.EducationlevelGRPC;
import com.example.grpc.ExperienceGRPC;
import com.example.grpc.ExperiencetypeGRPC;
import com.example.grpc.InterestFieldGRPC;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class OnBoardingDTO {



    String workplace;
    Boolean  publictransportationTF;
    List<InterestFieldGRPC> interestfields;
    ExperiencetypeGRPC experiencetype;
    List<ExperienceGRPC> experiences;
    List<String> skills; //
    EducationlevelGRPC educationlevel; // 학력사항
    String supportneeds; //특별히 도움이 필요한 것들
    String selfintroduce; //자기소개

}
