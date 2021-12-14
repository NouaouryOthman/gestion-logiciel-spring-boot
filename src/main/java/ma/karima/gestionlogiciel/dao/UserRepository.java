package ma.karima.gestionlogiciel.dao;

import ma.karima.gestionlogiciel.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findByEmailContains(String email, Pageable pageable);
}
