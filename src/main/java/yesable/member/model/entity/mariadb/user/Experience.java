package yesable.member.model.entity.mariadb.user;


import jakarta.persistence.*;
import lombok.*;
import yesable.member.enums.user.Experiencetype;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder //Mapping에서 빌더패턴을 위해 추가
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long experienceSeq;

    @ManyToOne
    @JoinColumn(name = "private_user_id")
    private PrivateUser privateUser;

    @Enumerated(EnumType.STRING)
    private Experiencetype experiencetype;

    private String companyname;

    private LocalDate startdate;

    private LocalDate enddate;

    private String department; //직무 종류

    private String jobdescription; //주요 업무 내용


    public Experience() {

    }

}
