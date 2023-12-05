package ufc.sonoroteca.ui;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = "ufc.sonoroteca")
@EntityScan("ufc.sonoroteca.entity")
@EnableJpaRepositories("ufc.sonoroteca.dao.jpa")
// @EnableMongoRepositories("ufc.sonoroteca.dao.mongo")
@Slf4j
public class SonorotecaApplication implements CommandLineRunner {
	@Autowired
	private MenuUsuario menuUsuario;

	@Autowired
	private MenuMusica menuMusica;
	@Autowired
	private MenuPlaylist menuPlaylist;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SonorotecaApplication.class);
		builder.headless(false).run(args);
	}

	public void run(String... args) throws Exception {
		StringBuilder menu = new StringBuilder("""
				Menu Principal :
				1 - Usuário
				2 - Música
				3 - Playlist
				4 - Sair""");

		char opcao = '0';
		do {
			try {
				opcao = JOptionPane.showInputDialog(menu).charAt(0);
				switch (opcao) {
					case '1': // Usuario
						menuUsuario.menu();
						break;
					case '2': // Musica
						menuMusica.menu();
						break;
					case '3': // playlist
						menuPlaylist.menu();
						break;
					case '4': // Sair
						break;
					default:
						JOptionPane.showMessageDialog(null, "Opção Inválida");
						break;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
			}

		} while (opcao != '4');
	}

}
