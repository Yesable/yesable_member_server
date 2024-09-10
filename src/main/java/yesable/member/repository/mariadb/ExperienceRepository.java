package yesable.member.repository.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yesable.member.model.entity.mariadb.user.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
