package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoEstilo;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author Júlio Moreira Blás de Barros (200491)
 * @since 15/06/18
 */
public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfiguracaoEstilo fontes = ConfiguracaoEstilo.getInstance();

        Group raiz = new Group();
        Scene menu = new Scene(raiz, 1000, 400);
        menu.getStylesheets().add("resources/font.css");

        Label lblTitulo = new Label("Treta Camp");

        Label lblInicio = new Label("Jogar");
        lblInicio.setCursor(Cursor.HAND);
        lblInicio.setOnMouseClicked((evt) -> {
            try {
                SelecaoDePersonagens.iniciar(primaryStage);
            } catch (Exception e) {
                new Alert(
                    Alert.AlertType.ERROR,
                    "Erro carregando selecao de personagens: " + e.getMessage())
                .show();
                primaryStage.close();
            }
        });

        Label lblSair = new Label("Sair");
        lblSair.setOnMouseClicked((evt) -> {
            primaryStage.close();
        });
        lblSair.setCursor(Cursor.HAND);

        lblTitulo.getStyleClass().add(fontes.PIXEL_LOVE_BIG);
        lblInicio.getStyleClass().add(fontes.PIXEL_LOVE);
        lblSair.getStyleClass().add(fontes.PIXEL_LOVE);

        ImageView imgFundo = new ImageView(new Image(
            new FileInputStream("src/resources/intro.gif")
        ));

        raiz.getChildren().addAll(imgFundo, lblTitulo, lblInicio, lblSair);

        imgFundo.setTranslateX(220);
        imgFundo.setTranslateY(150);

        lblTitulo.setTranslateX(260);
        lblTitulo.setTranslateY(100);

        lblInicio.setTranslateX(170);
        lblInicio.setTranslateY(200);

        lblSair.setTranslateX(470);
        lblSair.setTranslateY(200);

        primaryStage.setWidth(700);
        primaryStage.setHeight(400);

        primaryStage.setResizable(false);

        primaryStage.setTitle("Treta Camp - Menu");
        primaryStage.setScene(menu);
        primaryStage.show();
    }
}