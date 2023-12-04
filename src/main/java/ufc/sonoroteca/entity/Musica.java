package ufc.sonoroteca.entity;

import lombok.*;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.*;

@Document
@Entity
@Table(name = "musica")
@NoArgsConstructor
@AllArgsConstructor
@Data

//consulta nomeada

public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //auto incremento no banco de dados
    private String id;

    @NonNull
    private Integer duracaoSec;
    @NonNull
    private String genero;
    @NonNull
    private String anoLancamento;
    @NonNull
    private String cantor;
    @NonNull
    private String titulo;

    @ManyToMany(mappedBy = "musicas") //uma música pode estar em várias playlists
    private List<Playlist> playlists;

    @Override
    public String toString(){ //método para imprimir os dados da música
        return "Musica [Duração: " + duracaoSec +
                ", Gênero: " + genero +
                ", Ano de Lançamento: " + anoLancamento +
                ", Cantor: " + cantor +
                ", Título: " + titulo + "]";
    }
}
