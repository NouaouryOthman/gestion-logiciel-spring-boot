package ma.karima.gestionlogiciel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Email
    private String email;
    private String password;
    private String nom;
    private String prenom;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Role> roles;
    private boolean active;
    private String langue;
    @OneToMany(mappedBy = "user")
    private Collection<Logiciel> logiciels;
    private boolean firstTimeOnline;
}
