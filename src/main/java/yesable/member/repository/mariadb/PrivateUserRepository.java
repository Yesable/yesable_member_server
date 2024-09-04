package yesable.member.repository.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yesable.member.model.entity.mariadb.user.PrivateUser;

@Repository
public interface PrivateUserRepository extends JpaRepository<PrivateUser, Long> {
}
