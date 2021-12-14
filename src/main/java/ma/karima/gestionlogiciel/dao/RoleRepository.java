package ma.karima.gestionlogiciel.dao;

import ma.karima.gestionlogiciel.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
