package yesable.member.repository.mariadb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yesable.member.model.entity.mariadb.user.CompanyUser;

@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser,Long> {
}
