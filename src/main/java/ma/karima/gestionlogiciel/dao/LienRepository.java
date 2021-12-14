package ma.karima.gestionlogiciel.dao;

import ma.karima.gestionlogiciel.entities.Lien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LienRepository extends JpaRepository<Lien, Long> {
    @Query("select l from Lien l where l.logiciel.id=:x")
    Lien liensLogiciel(@Param("x") Long id);
}
