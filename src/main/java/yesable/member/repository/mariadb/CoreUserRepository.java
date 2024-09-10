package yesable.member.repository.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yesable.member.model.entity.mariadb.user.CoreUser;

@Repository
public interface CoreUserRepository extends JpaRepository<CoreUser, Long> {

<<<<<<< HEAD
    CoreUser findById(String id);
=======
    CoreUser findByID(String id);
>>>>>>> temp
}
