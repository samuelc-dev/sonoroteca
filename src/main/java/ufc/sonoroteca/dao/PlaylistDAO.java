package ufc.sonoroteca.dao;

import java.util.List;
import java.util.Optional;

import ufc.sonoroteca.entity.Playlist;
import ufc.sonoroteca.entity.Usuario;

public interface PlaylistDAO {

    public Playlist findFirstById(String playlistID);

    public List<Playlist> findByUsuarioId(String idUser);

    public List<Playlist> findByNomePlay(String nomePlay);

    public Optional<Playlist> findById(String id);

    public Playlist save(Playlist playlist);

    public void delete(Playlist playlist);

    public List<Playlist> findAll();

    List<Playlist> findByUsuario(Usuario usuario);

    // List<Playlist> findAllCantor();

    // Playlist findByIdWithMusics(String playlistId);
}
