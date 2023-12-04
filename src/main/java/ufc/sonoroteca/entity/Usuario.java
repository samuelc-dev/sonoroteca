package ufc.sonoroteca.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document
@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
// consulta nomeada

@NamedQueries({
        @NamedQuery(name = "totalUsuarios", query = "SELECT COUNT(*) AS total_usuarios FROM Usuario")
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    @NonNull
    private String email;
    @NonNull
    private String nome;

    // um usuário pode ter várias playlists
    // @OneToMany(mappedBy = "usuario")
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists;

    @Override
    public String toString() {
        return "Usuário [id=" + id + ", nome=" + nome + ", email=" + email + "]";
    }

}
