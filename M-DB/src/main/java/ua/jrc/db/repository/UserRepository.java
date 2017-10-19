package ua.jrc.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.jrc.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
