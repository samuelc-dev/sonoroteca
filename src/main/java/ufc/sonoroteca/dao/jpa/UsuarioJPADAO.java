package ufc.sonoroteca.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.sonoroteca.dao.UsuarioDAO;
import ufc.sonoroteca.entity.Usuario;

@Repository
public interface UsuarioJPADAO extends UsuarioDAO, JpaRepository<Usuario, String> {

    // jpa
    public Usuario findFirstByEmail(String email);

    List<Usuario> findAll();

    // jpa
    public Usuario findFristById(String usuarioID);

    // Consulta Native Query para encontrar usuario por nome
    @Query(value = "SELECT * FROM usuario WHERE nome = :nome", nativeQuery = true)
    List<Usuario> findByNome(String nome);

    // Named Query
    @Query(name = "totalUsuarios")
    int totalUsuarios();

}
