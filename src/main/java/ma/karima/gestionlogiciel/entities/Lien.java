package ma.karima.gestionlogiciel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Lien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telechargLogiciel;
    private String releaseNotes;
    private String telechargEnvElecIEC;
    private String guideInstallation;
    private String documentationTechnique;
    private String planningFormation;
    private String bonDeCommande;
    private String SEEWebCatalog;
    private String telechargSEEWebCatalog;
    private String telechargLogicielSEE;
    @OneToOne
    private Logiciel logiciel;
}
