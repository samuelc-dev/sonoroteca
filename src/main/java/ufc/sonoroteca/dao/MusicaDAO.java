package ufc.sonoroteca.dao;

import java.util.List;
import java.util.Optional;

import ufc.sonoroteca.entity.Musica;

public interface MusicaDAO {

    public Musica findFristById(String musicaID);

    List<Musica> findAllBygenero(String genero);

    List<Musica> findByPlaylistsId(String idPlaylist);

    List<Musica> findAllBycantor(String cantor);

    public void save(Musica musica);

    public void deleteById(String id);

    public Optional<Musica> findById(String id);

    List<Musica> findAll();

    List<String> findAllCantores();

    List<String> findAllGeneros();

}
