package yesable.member.model.entity.mariadb.user;



import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import yesable.member.enums.user.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="usertype")
@Getter
public class CoreUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    private String id;

    private String password;

   // private String usertype; //개인, 기업회원 구분 DiscriminatorColumn으로 대체
    
    private String email;

    private String phoneNumber;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Transient
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<GrantedAuthority> getAuthorities() { //기존의 ? extends Granted.. 설정시 MapStruct에 의한 자동 매핑 과정에서 Producer extends, Consumer Super 원칙에 위배되어 에러가 남.
        return this.authorities;
    }



    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
