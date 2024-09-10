package yesable.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import yesable.member.enums.user.Gender;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@SuperBuilder
public class CoreUserDTO {
<<<<<<< HEAD
    private Long userSeq;
=======
>>>>>>> temp
    private String id;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Collection<GrantedAuthority> authorities;
}
