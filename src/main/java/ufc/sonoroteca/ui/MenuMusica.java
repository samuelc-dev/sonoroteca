package ufc.sonoroteca.ui;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ufc.sonoroteca.dao.MusicaDAO;
import ufc.sonoroteca.entity.Musica;

@Component
public class MenuMusica {

    @Autowired
    private MusicaDAO musicaDAO;

    // log
    private static final Logger log = LoggerFactory.getLogger(SonorotecaApplication.class);

    public void obterMusica(Musica musica) {
        musica.setTitulo(JOptionPane.showInputDialog("Titulo", musica.getTitulo()));
        musica.setCantor(JOptionPane.showInputDialog("Cantor(a)", musica.getCantor()));
        musica.setAnoLancamento(
                JOptionPane.showInputDialog("Ano Lançamento - 'dd-MM-aaaa'", musica.getAnoLancamento()));
        musica.setGenero(JOptionPane.showInputDialog("Genero", musica.getGenero()));
        musica.setDuracaoSec(
                Integer.parseInt(JOptionPane.showInputDialog("Duração em segundos", musica.getDuracaoSec())));
    }

    public void listaMusica(Musica musica) {
        JOptionPane.showMessageDialog(null, musica == null ? "Nenhuma música encontrada" : musica);
    }

    public void listaMusicas(List<Musica> musicas) {
        StringBuilder listagem = new StringBuilder();
        for (Musica ms : musicas) {
            listagem.append(ms.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, listagem.isEmpty() ? "Nenhuma música encontrada" : listagem);
    }

    // Método para exibir a JComboBox de gêneros musicais
    private String escolherGenero(List<String> generosMusicais) {
        JComboBox<String> generoComboBox = new JComboBox<>(generosMusicais.toArray(new String[0]));
        JPanel panel = new JPanel();
        panel.add(new JLabel("Escolha o gênero:"));
        panel.add(generoComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Escolha o Gênero",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (String) generoComboBox.getSelectedItem();
        }

        return null;
    }

    // Método para exibir a JComboBox de cantores (obtidos do banco de dados)
    private String escolherCantor(List<String> cantores) {
        JComboBox<String> cantorComboBox = new JComboBox<>(cantores.toArray(new String[0]));
        JPanel panel = new JPanel();
        panel.add(new JLabel("Escolha o cantor:"));
        panel.add(cantorComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Escolha o Cantor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (String) cantorComboBox.getSelectedItem();
        }

        return null;
    }

    // Método para obter todas as músicas e seus IDs
    private Musica escolherMusica(List<Musica> musicas) {
        JComboBox<Musica> musicaComboBox = new JComboBox<>(musicas.toArray(new Musica[0]));
        JPanel panel = new JPanel();
        panel.add(new JLabel("Escolha a música:"));
        panel.add(musicaComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Escolha a Música",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return (Musica) musicaComboBox.getSelectedItem();
        }

        return null;
    }

    public void menu() {
        System.out.println("Entrou em música");
        StringBuilder menu = new StringBuilder("Menu Música\n")
                .append("1 - Inserir música\n")
                .append("2 - Atualizar música\n")
                .append("3 - Remover música\n")
                .append("4 - Exibir por id\n")
                .append("5 - Exibir todas músicas de determinado gênero\n")
                .append("6 - Exibir todas as músicas cadastradas\n")
                .append("7 - Exibir todas músicas de determinado cantor\n")
                .append("8 - Voltar ao Menu anterior");

        String opcao = "0";

        do {
            try {
                Musica musica;
                String genero;
                opcao = String.valueOf(JOptionPane.showInputDialog(menu).charAt(0));
                switch (opcao) {
                    case "1": // Inserir
                        musica = new Musica();
                        obterMusica(musica);
                        musicaDAO.save(musica);
                        break;
                    case "2": // Atualizar por id
                        List<Musica> todasMusicas = musicaDAO.findAll();
                        Musica musicaParaAtualizar = escolherMusica(todasMusicas);
                        if (musicaParaAtualizar != null) {
                            obterMusica(musicaParaAtualizar);
                            musicaDAO.save(musicaParaAtualizar);
                            JOptionPane.showMessageDialog(null, "Música atualizada com sucesso.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhuma música selecionada.");
                        }
                        break;
                    case "3":
                        List<Musica> todasMusicasRemover = musicaDAO.findAll();
                        Musica musicaParaRemover = escolherMusica(todasMusicasRemover);
                        if (musicaParaRemover != null) {
                            musicaDAO.deleteById(musicaParaRemover.getId());
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhuma música selecionada.");
                        }
                        break;
                    case "4": // Exibir por id
                        List<Musica> todasMusicasExibir = musicaDAO.findAll();
                        Musica musicaParaExibir = escolherMusica(todasMusicasExibir);
                        if (musicaParaExibir != null) {
                            Hibernate.initialize(musicaParaExibir.getPlaylists());
                            listaMusica(musicaParaExibir);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhuma música selecionada.");
                        }
                        break;
                    case "5": // Exibir por gênero
                        List<String> todosGeneros = musicaDAO.findAllGeneros();
                        if (!todosGeneros.isEmpty()) {
                            genero = escolherGenero(todosGeneros);
                            if (genero != null) {
                                List<Musica> musicasPorGenero = musicaDAO.findAllBygenero(genero);
                                if (!musicasPorGenero.isEmpty()) {
                                    listaMusicas(musicasPorGenero);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Nenhuma música encontrada para o gênero selecionado.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum gênero cadastrado.");
                        }
                        break;
                    case "6": // Exibir todas as musicas
                        List<Musica> musicas = musicaDAO.findAll();
                        if (!musicas.isEmpty()) {
                            listaMusicas(musicas);
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhuma música encontrada.");
                        }
                        break;
                    case "7": // Exibir todas músicas de determinado cantor
                        List<String> todosCantores = musicaDAO.findAllCantores();
                        if (!todosCantores.isEmpty()) {
                            String cantorEscolhido = escolherCantor(todosCantores);
                            if (cantorEscolhido != null) {
                                List<Musica> musicasPorCantor = musicaDAO.findAllBycantor(cantorEscolhido);
                                if (!musicasPorCantor.isEmpty()) {
                                    listaMusicas(musicasPorCantor);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Nenhuma música encontrada para o cantor selecionado.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Nenhum cantor cadastrado.");
                        }
                        break;
                    case "8":
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção Inválida");
                        break;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        } while (!opcao.equals("8"));
    }
}
