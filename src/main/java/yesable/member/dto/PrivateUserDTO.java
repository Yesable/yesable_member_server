package yesable.member.dto;

import com.example.grpc.PrivateUserGRPC;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import yesable.member.enums.user.Disabilitytype;
import yesable.member.enums.user.Educationlevel;
import yesable.member.enums.user.Interestfield;
import yesable.member.enums.user.Worktype;
import yesable.member.model.entity.mariadb.user.Experience;

import java.util.List;
import java.util.Set;

@Getter
@SuperBuilder
public class PrivateUserDTO extends CoreUserDTO{
    private String username;
    private String location;
    private Set<Interestfield> interestField;
    private Set<Worktype> workType;
    private Set<String> skills;
    private Educationlevel educationlevel;
    private String personality;
    private String hobbies;
    private String supportneeds;
    private Disabilitytype disabilitytype;

    private Set<Experience> experiences;
}
