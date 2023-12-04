package ufc.sonoroteca.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ufc.sonoroteca.dao.UsuarioDAO;
import ufc.sonoroteca.entity.Usuario;

@Repository
public interface UsuarioMongoDAO extends UsuarioDAO, MongoRepository<Usuario, String> {

    // Método para encontrar o primeiro usuário pelo email
    Usuario findFirstByEmail(String email);

    // Método para encontrar o primeiro usuário pelo ID
    Usuario findFirstById(String usuarioID);

    // Consulta para encontrar usuários por nome usando Query
    @Query("{'nome': ?0}")
    List<Usuario> findByNome(String nome);

    // Consulta para contar o total de usuários
    @Query(value = "{}", count = true)
    int totalUsuarios();
}
