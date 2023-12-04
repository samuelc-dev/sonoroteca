package ufc.sonoroteca.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.sonoroteca.dao.MusicaDAO;
import ufc.sonoroteca.entity.Musica;

@Repository
public interface MusicaMongoDAO extends MusicaDAO, MongoRepository<Musica, String> {

    Musica findFirstById(String musicaID);

    List<Musica> findAllByGenero(String genero);

    @Query("{'_id': { $in: ?0 }}")
    List<Musica> findByPlaylistId(String idPlaylist);

    List<Musica> findAll();

    List<Musica> findAllByCantor(String cantor);

    @Query("{'_id': { $in: ?0 }}")
    public List<String> findAllCantores();

    @Query("{'_id': { $in: ?0 }}")
    public List<String> findAllGeneros();
}
