package ma.karima.gestionlogiciel.dao;

import ma.karima.gestionlogiciel.entities.Logiciel;
import ma.karima.gestionlogiciel.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogicielRepository extends JpaRepository<Logiciel, Long> {
    @Query("select c from Logiciel c where c.user.id=:x")
    Page<Logiciel> listeLogiciels(@Param("x") Long id, Pageable page);
    Logiciel findTopByOrderByIdDesc();
    Page<Logiciel> findByNomContainsAndGratuitIsTrue(String nom, Pageable pageable);
}
