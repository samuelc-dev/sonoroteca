package ufc.sonoroteca.ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufc.sonoroteca.dao.MusicaDAO;
import ufc.sonoroteca.dao.PlaylistDAO;
import ufc.sonoroteca.dao.UsuarioDAO;
import ufc.sonoroteca.entity.Musica;
import ufc.sonoroteca.entity.Playlist;
import ufc.sonoroteca.entity.Usuario;

@Component
public class MenuPlaylist {

    @Autowired
    private PlaylistDAO playlistDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private MusicaDAO musicaDAO;

    private static final Logger log = LoggerFactory.getLogger(SonorotecaApplication.class);

    public void listaPlaylist(Playlist playlist) {
        JOptionPane.showMessageDialog(null, playlist == null ? "Nenhuma usuário encontrado" : playlist.toString());
    }

    public void listaMusicas(List<Musica> musica) {
        StringBuilder listagem = new StringBuilder();
        for (Musica ms : musica) {
            listagem.append(ms.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.isEmpty() ? "Nenhuma música encontrada" : listagem);
    }

    public void listaPlaylists(List<Playlist> playlists) {
        StringBuilder listagem = new StringBuilder();
        for (Playlist pl : playlists) {
            listagem.append(pl.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.isEmpty() ? "Nenhuma playlist encontrado" : listagem);
    }

    public void menu() {
        System.out.println("Entrou na playlist");
        StringBuilder menu = new StringBuilder("Menu Playlist\n")
                .append("1 - Criar Playlist\n")
                .append("2 - Adicionar uma música a playlist\n")
                .append("3 - Remover playlist\n")
                .append("4 - Exibir por nome\n")
                .append("5 - Exibir todas as playlists do usuário\n")
                .append("6 - Exibir as músicas da playlist\n")
                .append("7 - Menu anterior");

        String opcao = "0";
        do {
            try {
                String id;
                opcao = String.valueOf(JOptionPane.showInputDialog(menu).charAt(0));
                switch (opcao) {
                    case "1": // Criar playlist
                        criarPlaylist();
                        break;
                    case "2": // Adicionar uma música a playlist
                        adicionarMusicaAPlaylist();
                        break;
                    case "3": // Remover por id
                        removerPlaylist();
                        break;
                    case "4": // Encontrar playlist por nome (Consulta Nativa)
                        exibirPorNome();
                        break;
                    case "5": // Exibir todas as playlists do usuario
                        exibirTodasPlaylistsUsuario();
                        break;
                    case "6": // Exibir as músicas da playlist
                        exibirMusicasDaPlaylist();
                        break;
                    case "7": // Voltar ao menu anterior
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        } while (!opcao.equals("7"));
    }

    private void exibirPorNome() {
        // Obter playlists disponíveis
        List<Playlist> todasPlaylists = playlistDAO.findAll();

        if (todasPlaylists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há playlists disponíveis. Cadastre playlists antes de continuar.");
            return;
        }

        // Criar JComboBox para playlists
        JComboBox<Playlist> playlistComboBox = new JComboBox<>(todasPlaylists.toArray(new Playlist[0]));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Escolha a playlist para exibir por nome:"));
        panel.add(playlistComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Exibir Playlist por Nome",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Obter a playlist selecionada
            Playlist playlistSelecionada = (Playlist) playlistComboBox.getSelectedItem();
            listaPlaylists(List.of(playlistSelecionada));
        }
    }

    private void exibirTodasPlaylistsUsuario() {
        // Obter a lista de usuários cadastrados
        List<Usuario> usuarios = usuarioDAO.findAll();

        // Verificar se há usuários para escolher
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado. Cadastre um usuário antes de continuar.");
            return;
        }

        // Criar um array de strings para armazenar os emails dos usuários
        String[] emailsUsuarios = new String[usuarios.size()];
        for (int i = 0; i < usuarios.size(); i++) {
            emailsUsuarios[i] = usuarios.get(i).getEmail();
        }

        // Criar um JComboBox para escolher o usuário pelo email
        JComboBox<String> usuarioComboBox = new JComboBox<>(emailsUsuarios);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Escolha o usuário pelo email para exibir suas playlists:"));
        panel.add(usuarioComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Exibir Playlists do Usuário",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Obter o email do usuário selecionado
            String emailSelecionado = (String) usuarioComboBox.getSelectedItem();

            // Obter o usuário correspondente ao email
            Usuario usuarioSelecionado = usuarios.stream()
                    .filter(u -> u.getEmail().equals(emailSelecionado))
                    .findFirst()
                    .orElse(null);

            // Obter as playlists do usuário
            List<Playlist> playlistsUsuario = playlistDAO.findByUsuario(usuarioSelecionado);
            listaPlaylists(playlistsUsuario);
        }
    }

    private void exibirMusicasDaPlaylist() {

        List<Playlist> todasPlaylists = playlistDAO.findAll();

        if (todasPlaylists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há playlists disponíveis. Cadastre playlists antes de continuar.");
            return;
        }

        // Criar JComboBox para playlists
        JComboBox<Playlist> playlistComboBox = new JComboBox<>(todasPlaylists.toArray(new Playlist[0]));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Escolha a playlist para exibir as músicas:"));
        panel.add(playlistComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Exibir Músicas da Playlist",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Obter a playlist selecionada
            Playlist playlistSelecionada = (Playlist) playlistComboBox.getSelectedItem();

            // Obter as músicas da playlist
            List<Musica> musicasDaPlaylist = musicaDAO.findByPlaylistsId(playlistSelecionada.getId());
            listaMusicas(musicasDaPlaylist);
        }
    }

    private void adicionarMusicaAPlaylist() {

        // Obter músicas disponíveis
        List<Musica> todasMusicas = musicaDAO.findAll();
        // Obter playlists disponíveis
        List<Playlist> todasPlaylists = playlistDAO.findAll();

        if (todasMusicas.isEmpty() || todasPlaylists.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Não há músicas ou playlists disponíveis. Cadastre músicas e playlists antes de continuar.");
            return;
        }

        // Criar JComboBox para músicas
        JComboBox<Musica> musicaComboBox = new JComboBox<>(todasMusicas.toArray(new Musica[0]));
        // Criar JComboBox para playlists
        JComboBox<Playlist> playlistComboBox = new JComboBox<>(todasPlaylists.toArray(new Playlist[0]));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Escolha a música:"));
        panel.add(musicaComboBox);
        panel.add(new JLabel("Escolha a playlist:"));
        panel.add(playlistComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Música à Playlist",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Obter a música e a playlist selecionadas
            Musica musicaSelecionada = (Musica) musicaComboBox.getSelectedItem();
            Playlist playlistSelecionada = (Playlist) playlistComboBox.getSelectedItem();

            // Verificar se a lista de músicas da playlist é nula e inicializá-la se
            // necessário
            List<Musica> musicas = playlistSelecionada.getMusicas();
            if (musicas == null) {
                musicas = new ArrayList<>();
            }

            // Adicionar a música à lista de músicas da playlist
            musicas.add(musicaSelecionada);
            playlistSelecionada.setMusicas(musicas);
            playlistDAO.save(playlistSelecionada);

            JOptionPane.showMessageDialog(null, "Música adicionada à playlist com sucesso!");
        }
    }

    private void criarPlaylist() {
        String nomeplay = JOptionPane.showInputDialog("Nome da playlist");
        String descricao = JOptionPane.showInputDialog("Descrição");

        // Obter a lista de usuários cadastrados
        List<Usuario> usuarios = usuarioDAO.findAll();

        // Verificar se há usuários para escolher
        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum usuário cadastrado. Cadastre um usuário antes de criar uma playlist.");
            return;
        }

        // Criar um array de strings para armazenar os emails dos usuários
        String[] emailsUsuarios = new String[usuarios.size()];
        for (int i = 0; i < usuarios.size(); i++) {
            emailsUsuarios[i] = usuarios.get(i).getEmail();
        }

        // Criar um JComboBox para escolher o usuário pelo email
        JComboBox<String> usuarioComboBox = new JComboBox<>(emailsUsuarios);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Escolha o usuário pelo email:"));
        panel.add(usuarioComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Escolha o Usuário",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Obter o email do usuário selecionado
            String emailSelecionado = (String) usuarioComboBox.getSelectedItem();

            // Obter o usuário correspondente ao email
            Usuario usuarioSelecionado = usuarios.stream()
                    .filter(u -> u.getEmail().equals(emailSelecionado))
                    .findFirst()
                    .orElse(null);

            // Criar a nova playlist
            Playlist newPlay = new Playlist();
            newPlay.setNomePlay(nomeplay);
            newPlay.setDescricao(descricao);
            newPlay.setUsuario(usuarioSelecionado);
            playlistDAO.save(newPlay);

            JOptionPane.showMessageDialog(null, "Playlist criada com sucesso!");
        }
    }

    private void removerPlaylist() {
        // Obter playlists disponíveis
        List<Playlist> todasPlaylists = playlistDAO.findAll();

        if (todasPlaylists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há playlists disponíveis. Cadastre playlists antes de continuar.");
            return;
        }

        // Criar JComboBox para playlists
        JComboBox<Playlist> playlistComboBox = new JComboBox<>(todasPlaylists.toArray(new Playlist[0]));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Escolha a playlist a ser removida:"));
        panel.add(playlistComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Remover Playlist",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Obter a playlist selecionada
            Playlist playlistSelecionada = (Playlist) playlistComboBox.getSelectedItem();

            // Confirmar a remoção com o usuário
            int confirmacao = JOptionPane.showConfirmDialog(null,
                    "Você tem certeza que deseja remover a playlist \"" + playlistSelecionada.getNomePlay() + "\"?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                // Remover a playlist
                playlistDAO.delete(playlistSelecionada);
                JOptionPane.showMessageDialog(null, "Playlist removida com sucesso!");
            }
        }
    }
}
