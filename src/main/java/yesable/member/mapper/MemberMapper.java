package yesable.member.mapper;

import com.example.grpc.*;


import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.ProtocolStringList;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import yesable.member.dto.CoreUserDTO;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.enums.user.*;
import yesable.member.enums.user.Experiencetype;
import yesable.member.enums.user.Gender;
import yesable.member.model.entity.mariadb.user.CoreUser;
import yesable.member.model.entity.mariadb.user.Experience;
import yesable.member.model.entity.mariadb.user.PrivateUser;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Mapper(collectionMappingStrategy=CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface MemberMapper {
    //toDTO: gRPC의 각 엔티티를 JPA 스타일의 엔티티로 전환하기 위해 일단 DTO로 변경
    //toEntity : DTO형식의 엔티티를 JPA스타일의 엔티티로 전환
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    CoreUserDTO toDTO(CoreUserGRPC entity);



    CoreUser toEntity(CoreUserDTO dto);




    @Mapping(source="coreUser.id",target="id")
    @Mapping(source="coreUser.password",target="password")
    @Mapping(source="coreUser.email",target="email")
    @Mapping(source="coreUser.phoneNumber",target="phoneNumber")
    @Mapping(source="coreUser.name",target="name")
    @Mapping(source="coreUser.gender",target="gender")
    @Mapping(source="coreUser.dateOfBirth",target="dateOfBirth")
    @Mapping(source="coreUser.authorities",target="authorities")
    PrivateUserDTO grpcToDto(PrivateUserGRPC grpc);


    @Mapping(source = "id", target = "coreUser.id")
    @Mapping(source = "password", target = "coreUser.password")
    @Mapping(source = "email", target = "coreUser.email")
    @Mapping(source = "phoneNumber", target = "coreUser.phoneNumber")
    @Mapping(source = "name", target = "coreUser.name")
    @Mapping(source = "gender", target = "coreUser.gender")
    @Mapping(source = "dateOfBirth", target = "coreUser.dateOfBirth")
    PrivateUserGRPC dtoToGrpc(PrivateUserDTO dto);

    PrivateUser dtoToEntity(PrivateUserDTO dto);






    PrivateUserDTO entitytoDto(PrivateUser entity);






    //---------------------------------------이하 추가 매핑 메소드-------------------------- f


    default Collection<GrantedAuthority> map(ProtocolStringList ls) {

        return ls.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(ArrayList::new));

    }


    default ProtocolStringList map(Set<String> set) {
        ProtocolStringList result=new LazyStringArrayList();


        result.addAll(set);
        return result;
    }
    default Experience map(ExperienceGRPC value) {
            if (value == null) {
                return null;
            }

            // result에 들어갈 LocalDate 생성
            LocalDate resultStartDate = LocalDate.of(value.getStartdate().getYear(), value.getStartdate().getMonth(), value.getStartdate().getDay());
            LocalDate resultEndDate = LocalDate.of(value.getEnddate().getYear(), value.getEnddate().getMonth(), value.getEnddate().getDay());

            // Experience 객체 빌드
            Experience result = Experience.builder()
                    .experienceSeq(value.getUserSeq())
                    .companyname(value.getCompanyname())
                    .experiencetype(Experiencetype.valueOf(value.getExperiencetype().name()))
                    .startdate(resultStartDate)
                    .enddate(resultEndDate)
                    .department(value.getDepartment())
                    .jobdescription(value.getJobdescription())
                    .build();



            return result;
        }




    default LocalDate map(LocalDateGRPC value) {
        if (value == null) {
            return null;
        }
        System.out.println("Mapping LocalDateGRPC: " + value);

        int year = value.getYear();
        int month = value.getMonth();
        int day = value.getDay();

        // 유효성 검사
        if (year < 1 || year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Invalid year value: " + year);
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month value: " + month);
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day value: " + day);
        }

        LocalDate result = LocalDate.of(year, month, day);

        System.out.println("Mapped LocalDate: " + result);

        return result;
    }



    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Educationlevel map(EducationlevelGRPC educationlevel);

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Disabilitytype map(DisabilitytypeGRPC disabilitytype);

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Worktype map(WorkTypeGRPC workType);

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Interestfield map(InterestFieldGRPC interestField);

    @ValueMapping(source = "UNRECOGNIZED",target = "UNKNOWN")
    Gender map(GenderGRPC gender);





    //---

    default LocalDateGRPC map(LocalDate dateOfBirth) {

        LocalDateGRPC result= LocalDateGRPC.newBuilder()
                .setYear(dateOfBirth.getYear())
                .setMonth(dateOfBirth.getMonthValue())
                .setDay(dateOfBirth.getDayOfMonth())
                .build();

        return result;
    }





    @ValueMapping(source="UNKNOWN",target="UNRECOGNIZED")
    GenderGRPC map(Gender gender);

    @ValueMapping(source = "UNKNOWN", target = "UNRECOGNIZED") // 적절한 값으로 매핑
    InterestFieldGRPC map(Interestfield interestField);

    @ValueMapping(source = "UNKNOWN", target = "UNRECOGNIZED") // 적절한 값으로 매핑
    WorkTypeGRPC map(Worktype workType);

    @ValueMapping(source="UNKNOWN", target="UNRECOGNIZED")
    EducationlevelGRPC map(Educationlevel educationlevel);

    @ValueMapping(source="UNKNOWN", target="UNRECOGNIZED")
    DisabilitytypeGRPC map(Disabilitytype disabilitytype);


    ExperienceGRPC map(Experience experience);








}
