package yesable.member.mapper;

import com.example.grpc.*;
import com.google.protobuf.ProtocolStringList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import yesable.member.dto.CompanyUserDTO;
import yesable.member.dto.CoreUserDTO;
import yesable.member.dto.PrivateUserDTO;
import yesable.member.enums.user.*;
import yesable.member.model.entity.mariadb.user.CompanyUser;
import yesable.member.model.entity.mariadb.user.CoreUser;
import yesable.member.model.entity.mariadb.user.Experience;
import yesable.member.model.entity.mariadb.user.PrivateUser;

import java.time.LocalDate;
import java.util.Collection;
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
    @Mapping(source = "experiencesList",target="experiences")
    PrivateUserDTO grpcToDto(PrivateUserGRPC grpc);

    PrivateUser dtoToEntity(PrivateUserDTO dto);


    @Mapping(source="hrName",target="hr_name")
    @Mapping(source="hrPhone",target="hr_phone")
    @Mapping(source="hrEmail",target="hr_email")
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

        return Experience.builder()
                .experienceSeq(value.getId())
                .companyname(value.getCompanyname())
                .experiencetype(Experiencetype.valueOf(value.getExperiencetype().name()))
                .startDate(LocalDate.of(value.getStartdate().getYear(), value.getStartdate().getMonth(), value.getStartdate().getDay()))
                .endDate(LocalDate.of(value.getEnddate().getYear(), value.getEnddate().getMonth(), value.getEnddate().getDay()))
                .department(value.getDepartment())
                .jobDescription(value.getJobdescription())
                .build();
    }

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Educationlevel map(PrivateUserGRPC.Educationlevel educationlevel);

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Disabilitytype map(PrivateUserGRPC.Disabilitytype disabilitytype);

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Worktype map(PrivateUserGRPC.WorkType workType);

    @ValueMapping(source="UNRECOGNIZED", target="UNKNOWN")
    Interestfield map(PrivateUserGRPC.InterestField interestField);






}
