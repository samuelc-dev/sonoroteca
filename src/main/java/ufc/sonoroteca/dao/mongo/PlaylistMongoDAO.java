package ufc.sonoroteca.dao.mongo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ufc.sonoroteca.dao.PlaylistDAO;
import ufc.sonoroteca.entity.Playlist;
import ufc.sonoroteca.entity.Usuario;

@Repository
public interface PlaylistMongoDAO extends PlaylistDAO, MongoRepository<Playlist, String> {

    Playlist findFirstById(String playlistID);

    @Query("{ 'usuario.id' : ?0 }")
    List<Playlist> findByUsuarioId(String idUser);

    @Query("{ 'nomePlay' : ?0 }")
    List<Playlist> findByNomePlay(String nomePlay);

      // faça uma consulta que liste todas as playlists da tabela com jpa
    public List<Playlist> findAll();

    List<Playlist> findByUsuario(Usuario usuario);

    @Query("{$match: {id: ?0}}, {$lookup: { from: 'musicas', localField: 'id', foreignField: 'playlistId', as: 'musicas' }}")
    Playlist findByIdWithMusics(String playlistId); 

    
}
