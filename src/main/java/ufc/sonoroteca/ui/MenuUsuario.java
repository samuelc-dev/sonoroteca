package ufc.sonoroteca.ui;

import java.util.List;

import javax.swing.JOptionPane;

//log
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufc.sonoroteca.dao.UsuarioDAO;
import ufc.sonoroteca.entity.Usuario;

@Component
public class MenuUsuario {
    @Autowired
    private UsuarioDAO usuarioDAO;
    // log
    private static final Logger log = LoggerFactory.getLogger(SonorotecaApplication.class);

    public void obterUsuario(Usuario usuario) {
        usuario.setNome(JOptionPane.showInputDialog("Nome", usuario.getNome()));
        usuario.setEmail(JOptionPane.showInputDialog("Email", usuario.getEmail()));
    }

    public void listaUsuario(Usuario usuario) {
        JOptionPane.showMessageDialog(null, usuario == null ? "Nenhum usuário encontrado" : usuario.toString());
    }

    public void listaUsuarios(List<Usuario> usuarios) {
        StringBuilder listagem = new StringBuilder();
        for (Usuario us : usuarios) {
            listagem.append(us.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.isEmpty() ? "Nenhum usuário encontrado" : listagem);
    }

    public void menu() {
        System.out.println("Entrou no meu usuário");
        StringBuilder menu = new StringBuilder("Menu Usuário\n")
                .append("1 - Inserir\n")
                .append("2 - Atualizar por email\n")
                .append("3 - Remover por email\n")
                .append("4 - Exibir por email\n")
                .append("5 - Exibir todos os usuários\n")
                .append("6 - Encontrar usuário por nome\n")
                .append("7 - Exibir total de usuários\n")
                .append("8 - Menu anterior");

        String opcao = "0";

        do {
            try {
                Usuario usuario;
                String email;
                opcao = String.valueOf(JOptionPane.showInputDialog(menu).charAt(0));
                switch (opcao) {
                    case "1": // Inserir
                        usuario = new Usuario();
                        obterUsuario(usuario);
                        usuarioDAO.save(usuario);
                        break;
                    case "2": // Atualizar por email
                        email = JOptionPane.showInputDialog("Digite o email do usuário a ser alterado");
                        usuario = usuarioDAO.findFirstByEmail(email);
                        if (usuario != null) {
                            // Verificar a opção de cancelamento
                            int escolha = JOptionPane.showConfirmDialog(null,
                                    "Deseja atualizar o usuario com email: " + email + "?", "Confirmação",
                                    JOptionPane.YES_NO_OPTION);
                            if (escolha == JOptionPane.YES_OPTION) {
                                obterUsuario(usuario); // Permita ao usuário atualizar os detalhes do cliente
                                usuarioDAO.save(usuario);
                                JOptionPane.showMessageDialog(null, "Email atualizado com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Operação de atualização cancelada.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível atualizar, pois o usuário não foi encontrado.");
                        }
                        break;
                    case "3": // Remover por email
                        email = JOptionPane.showInputDialog("Email");
                        usuario = usuarioDAO.findFirstByEmail(email);
                        if (usuario != null) {
                            usuarioDAO.delete(usuario);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Não foi possível remover, pois o usuário não foi encontrado.");
                        }
                        break;
                    case "4": // Exibir por nome
                        email = JOptionPane.showInputDialog("E-mail");
                        usuario = usuarioDAO.findFirstByEmail(email);
                        listaUsuario(usuario);
                        break;
                    case "5": // Exibir todos
                        listaUsuarios(usuarioDAO.findAll());
                        break;
                    case "6": // Encontrar usuários por nome (Consulta Nativa)
                        String nomeConsultaNativa = JOptionPane.showInputDialog("Nome");
                        List<Usuario> ususarioPorNomeNativa = usuarioDAO.findByNome(nomeConsultaNativa);
                        listaUsuarios(ususarioPorNomeNativa);
                        break;
                    case "7":
                        int totalUser = usuarioDAO.totalUsuarios();
                        if (totalUser != 0) {
                            JOptionPane.showMessageDialog(null, totalUser);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não há usuários cadastrados");
                        }
                        break;
                    case "8": // Voltar ao menu anterior
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        } while (!opcao.equals("9"));
    }
}
