package yesable.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
public class CoreUserDTO {
    private String id;
    private String password;
    private String email;
    private String phoneNumber;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private Collection<GrantedAuthority> authorities;
}
