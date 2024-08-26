package yesable.member.model.entity.mariadb.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import yesable.member.enums.user.Experiencetype;

import java.time.LocalDate;

@Entity
@Getter
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

    private LocalDate startDate;

    private LocalDate endDate;

    private String department; //직무 종류

    private String jobDescription; //주요 업무 내용



}
