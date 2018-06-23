package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.config.ConfiguracaoEstilo;
import br.unicamp.tretacamp.modelo.Drego;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */

public class Campanha {

    public static void iniciar(Drego jogador, Stage primaryStage) throws Exception {
        ConfiguracaoEstilo fontes = ConfiguracaoEstilo.getInstance();
        ConfiguracaoDregos confDregos = ConfiguracaoDregos.getInstance();

        Group raiz = new Group();
        Scene campanha = new Scene(raiz, 1000, 400);
        campanha.getStylesheets().add("resources/font.css");



        primaryStage.setTitle("Treta Camp - Campanha " + jogador.getNome());
        primaryStage.setScene(campanha);
        primaryStage.show();
    }
}
