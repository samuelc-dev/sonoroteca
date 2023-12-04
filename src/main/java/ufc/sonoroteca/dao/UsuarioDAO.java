package ufc.sonoroteca.dao;

import java.util.List;
import java.util.Optional;

import ufc.sonoroteca.entity.Usuario;

public interface UsuarioDAO {

    public Usuario findFirstByEmail(String email);

    public Usuario findFirstById(String usuarioID);

    public List<Usuario> findByNome(String nome);

    public int totalUsuarios();

    public Usuario save(Usuario usuario);

    public void delete(Usuario usuario);

    public void deleteById(String id);

    public Optional<Usuario> findById(String id);

    public List<Usuario> findAll();

}
