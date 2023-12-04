package ufc.sonoroteca.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.sonoroteca.dao.PlaylistDAO;
import ufc.sonoroteca.entity.Playlist;
import ufc.sonoroteca.entity.Usuario;

@Repository
public interface PlaylistJPADAO extends PlaylistDAO, JpaRepository<Playlist, String> {

    public Playlist findFristById(String playlistID);

    // Consulta Native Query para encontrar playlist por id do usuário
    @Query(value = "SELECT * FROM playlist p WHERE p.usuario_id = :idUser", nativeQuery = true)
    List<Playlist> encontrarPlaylistPorId(String idUser);

    // Consulta Native Query para encontrar playlists por nome
    @Query(value = "SELECT * FROM playlist WHERE nomeplay = :nome", nativeQuery = true)
    List<Playlist> findByNome(String nome);

    // Consulta personalizada para encontrar uma playlist com suas músicas associadas
    @Query("SELECT p FROM Playlist p LEFT JOIN FETCH p.musicas WHERE p.id = :playlistId")
    Playlist findByIdWithMusics(@Param("playlistId") String playlistId);

    public List<Playlist> findAll();

    List<Playlist> findByUsuario(Usuario usuario);
}
