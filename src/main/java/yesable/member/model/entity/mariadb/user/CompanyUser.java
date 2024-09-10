package yesable.member.model.entity.mariadb.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import yesable.member.enums.user.Compclass;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class CompanyUser extends CoreUser{

    private String businessnumber;
    private String ceoname;
    private String businessarea;


    @Enumerated(EnumType.STRING)
    private Compclass compclass;
    private String hr_name;
    private String hr_phone;
    private String hr_email;


}
