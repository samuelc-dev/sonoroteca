package ufc.sonoroteca.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Entity
@Table(name = "playlist")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // auto incremento no banco de dados
    private String id;

    @Column(nullable = false) // não pode ser nulo
    private String nomePlay;

    private String descricao;

    @ManyToOne // muitas playlists podem pertencer a um usuário
    @JoinColumn(name = "usuario_id", nullable = false) // não pode ser nulo
    private Usuario usuario;

    @ManyToMany(cascade = CascadeType.ALL) // uma playlist pode ter várias músicas
    @JoinTable(name = "playlist_musica", joinColumns = @JoinColumn(name = "playlist_id"), inverseJoinColumns = @JoinColumn(name = "musica_id"))
    private List<Musica> musicas; // lista de músicas da playlist

    @Override
    public String toString() {
        return "Playlist [id=" + id + ", nome=" + nomePlay + "]";
    }

    public Playlist orElse(Object object) {
        return null;
    }
}
