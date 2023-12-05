package ufc.sonoroteca.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.sonoroteca.dao.MusicaDAO;
import ufc.sonoroteca.entity.Musica;

@Repository
public interface MusicaJPADAO extends MusicaDAO, JpaRepository<Musica, String> {

    public Musica findFristById(String musicaID);

    public List<Musica> findAllBygenero(String genero);

    public List<Musica> findByPlaylistsId(String playlistId);

    public List<Musica> findAll();

    public List<Musica> findAllBycantor(String cantor);

    // faça uma consulta que lista todos os cantores da tabela
    @Query("SELECT DISTINCT m.cantor FROM Musica m")
    public List<String> findAllCantores();

    // faça uma consulta que lista todos os cgeneros da tabela
    @Query("SELECT DISTINCT m.genero FROM Musica m")
    public List<String> findAllGeneros();

}
