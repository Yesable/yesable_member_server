package yesable.member.model.entity.mariadb.user;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import yesable.member.enums.user.Compclass;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
public class CompanyUser extends CoreUser {

    private String businessnumber; //10자리의 사업자 등록번호

    private String ceoname; //ceo의 이름

    private String businessarea; //사업분야 (set->string)

    @Enumerated(EnumType.STRING)
    private Compclass compclass; //기업 형태

    private String hr_name;

    private String hr_phone;

    private String hr_email;
/*
    @OneToOne(mappedBy = "companyuser")
    private CompanyLocation companylocation;
    */


}
