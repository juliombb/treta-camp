package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.config.ConfiguracaoEstilo;
import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.util.CarregadorDeImagens;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.util.Random;

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
        Drego inimigo = selectionarDregoInimigo(confDregos, jogador);

        campanha.getStylesheets().add("resources/font.css");
        Alert alerta = new Alert(
            Alert.AlertType.INFORMATION,
            "As forças malignas escolheram o drego " +
                ">>" + inimigo.getNome() + "<<"
            + " para te destruir. Boa Sorte!");
        alerta.show();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setWidth(screenSize.getWidth() * 0.95);
        primaryStage.setHeight(screenSize.getHeight() * 0.95);
        primaryStage.setX(screenSize.getWidth() * 0.025);
        primaryStage.setY(screenSize.getWidth() * 0.025);

        Double widthTela = primaryStage.getWidth();
        Double heightTela = primaryStage.getHeight();

        ///////////////// Configurando imagem ////////////////////////

        ImageView imgJog = null;
        imgJog = CarregadorDeImagens.carregar(jogador, primaryStage);
        if (imgJog == null) return;

        imgJog.setFitHeight(heightTela * 0.5);
        imgJog.setFitWidth(widthTela * 0.2);
        imgJog.setX(widthTela * 0.05);
        imgJog.setY(heightTela * 0.05);

        /////////////////////////////////////
        ImageView imgIni = null;
        imgIni = CarregadorDeImagens.carregar(inimigo, primaryStage);
        if (imgIni == null) return;

        imgIni.setFitHeight(heightTela * 0.5);
        imgIni.setFitWidth(widthTela * 0.2);
        imgIni.setX(widthTela * 0.95 - imgIni.getFitWidth());
        imgIni.setY(heightTela * 0.05);
        imgIni.setScaleX(-1.0);

        ///////////////////// Fim config imagem //////////////////////

        raiz.getChildren().addAll(imgJog, imgIni);

        primaryStage.setTitle("Treta Camp - Campanha " + jogador.getNome());
        primaryStage.setScene(campanha);
        primaryStage.show();
    }

    private static Drego selectionarDregoInimigo(ConfiguracaoDregos confDregos, Drego jogador) {
        Random rdm = new Random();
        Drego ret = jogador;
        while (ret.equals(jogador)) {
            ret = confDregos.dregos[rdm.nextInt(confDregos.dregos.length)];
        }
        return ret;
    }
}
