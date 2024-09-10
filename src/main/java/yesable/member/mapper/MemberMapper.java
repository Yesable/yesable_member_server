package yesable.member.mapper;

import com.example.grpc.*;


import com.google.protobuf.ProtocolStringList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import yesable.member.dto.CompanyUserDTO;
import yesable.member.dto.CoreUserDTO;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.enums.user.*;
import yesable.member.enums.user.Experiencetype;
import yesable.member.enums.user.Gender;
import yesable.member.model.entity.mariadb.user.CompanyUser;
import yesable.member.model.entity.mariadb.user.CoreUser;
import yesable.member.model.entity.mariadb.user.Experience;
import yesable.member.model.entity.mariadb.user.PrivateUser;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Mapper
public interface MemberMapper {
    //toDTO: gRPC의 각 엔티티를 JPA 스타일의 엔티티로 전환하기 위해 일단 DTO로 변경
    //toEntity : DTO형식의 엔티티를 JPA스타일의 엔티티로 전환
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "authorities", source = "authoritiesList")
    CoreUserDTO toDTO(CoreUserGRPC entity);



    CoreUser toEntity(CoreUserDTO dto);



    @Mapping(source = "interestFieldList", target = "interestField")
    @Mapping(source = "workTypeList", target = "workType")
    @Mapping(source = "skillsList", target = "skills")
    @Mapping(source = "experiencesList",target="experiences") //이하 super의 coreuser관련
    @Mapping(source="coreUser.id",target="id")
    @Mapping(source="coreUser.password",target="password")
    @Mapping(source="coreUser.email",target="email")
    @Mapping(source="coreUser.phoneNumber",target="phoneNumber")
    @Mapping(source="coreUser.name",target="name")
    @Mapping(source="coreUser.gender",target="gender")
    @Mapping(source="coreUser.dateOfBirth",target="dateOfBirth")
    @Mapping(source="coreUser.authoritiesList",target="authorities")
    PrivateUserDTO grpcToDto(PrivateUserGRPC grpc);

    PrivateUser dtoToEntity(PrivateUserDTO dto);


    @Mapping(source="coreUser.id",target="id")
    @Mapping(source="coreUser.password",target="password")
    @Mapping(source="coreUser.email",target="email")
    @Mapping(source="coreUser.phoneNumber",target="phoneNumber")
    @Mapping(source="coreUser.name",target="name")
    @Mapping(source="coreUser.gender",target="gender")
    @Mapping(source="coreUser.dateOfBirth",target="dateOfBirth")
    @Mapping(source="coreUser.authoritiesList",target="authorities")
    @Mapping(source="hrEmail",target="hr_email")
    @Mapping(source="hrPhone",target="hr_phone")
    @Mapping(source="hrName",target="hr_name")
    CompanyUserDTO grpcToDto(CompanyUserGRPC grpc);

    CompanyUser dtoToEntity(CompanyUserDTO dto);



    //---------------------------------------이하 추가 매핑 메소드-------------------------- f


    default Collection<GrantedAuthority> map(ProtocolStringList ls) {

        return ls.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toCollection(ArrayList::new));

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
                    .startDate(resultStartDate)
                    .endDate(resultEndDate)
                    .department(value.getDepartment())
                    .jobDescription(value.getJobdescription())
                    .build();

            // startdate 비교 및 출력
            System.out.println("Startdate 비교:");
            System.out.println("Value의 연도: " + value.getStartdate().getYear() + ", Result의 연도: " + resultStartDate.getYear());
            System.out.println("Value의 월: " + value.getStartdate().getMonth() + ", Result의 월: " + resultStartDate.getMonthValue());
            System.out.println("Value의 일: " + value.getStartdate().getDay() + ", Result의 일: " + resultStartDate.getDayOfMonth());

            // enddate 비교 및 출력
            System.out.println("Enddate 비교:");
            System.out.println("Value의 연도: " + value.getEnddate().getYear() + ", Result의 연도: " + resultEndDate.getYear());
            System.out.println("Value의 월: " + value.getEnddate().getMonth() + ", Result의 월: " + resultEndDate.getMonthValue());
            System.out.println("Value의 일: " + value.getEnddate().getDay() + ", Result의 일: " + resultEndDate.getDayOfMonth());


            return result;
        }




    default Date map(LocalDateGRPC value) {
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

        Date result = new Date();

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


    @ValueMapping(source = "UNRECOGNIZED",target = "UNKNOWN")
    Compclass map(CompclassGRPC gender);







}
