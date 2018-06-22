package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoFonte;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * @author Júlio Moreira Blás de Barros (200491)
 * @since 15/06/18
 */
public class Jogo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfiguracaoFonte fontes = ConfiguracaoFonte.getInstance();

        Group raiz = new Group();
        Scene menu = new Scene(raiz, 1000, 400);
        menu.getStylesheets().add("resources/font.css");

        Label lblTitulo = new Label("Treta Camp");

        Label lblInicio = new Label("Jogar");
        lblInicio.setCursor(Cursor.HAND);
        lblInicio.setOnMouseClicked((evt) -> {
            System.out.println("Jogo iniciado");
        });

        Label lblSair = new Label("Sair");
        lblSair.setOnMouseClicked((evt) -> {
            primaryStage.close();
        });
        lblSair.setCursor(Cursor.HAND);

        lblTitulo.getStyleClass().add(fontes.PIXEL_LOVE);
        lblInicio.getStyleClass().add(fontes.PIXEL_LOVE);
        lblSair.getStyleClass().add(fontes.PIXEL_LOVE);

        ImageView imgFundo = new ImageView(new Image(
            new FileInputStream("src/resources/intro.gif")
        ));

        raiz.getChildren().addAll(imgFundo, lblTitulo, lblInicio, lblSair);

        imgFundo.setTranslateX(150);
        imgFundo.setTranslateY(150);

        lblTitulo.setTranslateX(220);
        lblTitulo.setTranslateY(100);

        lblInicio.setTranslateX(100);
        lblInicio.setTranslateY(200);

        lblSair.setTranslateX(400);
        lblSair.setTranslateY(200);

        primaryStage.setWidth(550);
        primaryStage.setHeight(300);

        primaryStage.setTitle("Treta Camp - Menu");
        primaryStage.setScene(menu);
        primaryStage.show();
    }
}
