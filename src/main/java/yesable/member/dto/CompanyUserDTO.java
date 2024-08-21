package yesable.member.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import yesable.member.enums.user.Compclass;

@Getter
@SuperBuilder
public class CompanyUserDTO extends CoreUserDTO{
    private String businessnumber;
    private String ceoname;
    private String businessarea;
    private Compclass compclass;
    private String hr_name;
    private String hr_phone;
    private String hr_email;
}
