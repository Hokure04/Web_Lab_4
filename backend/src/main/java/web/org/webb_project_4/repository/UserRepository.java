package web.org.webb_project_4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.org.webb_project_4.model.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
