package ma.karima.gestionlogiciel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Logiciel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String motDePasse;
    private String numeroDeSerie;
    private Date dateAchat;
    @DecimalMin("10")
    private double prixAchat;
    @ManyToOne
    private User user;
    private boolean gratuit;
}
