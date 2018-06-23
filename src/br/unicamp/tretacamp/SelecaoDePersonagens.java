package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.config.ConfiguracaoFonte;
import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.Format;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */
public class SelecaoDePersonagens {

    private static final Double padding = 20.0;

    public static void iniciar(Stage primaryStage) throws Exception {
        ConfiguracaoFonte fontes = ConfiguracaoFonte.getInstance();
        ConfiguracaoDregos confDregos = ConfiguracaoDregos.getInstance();

        Group raiz = new Group();
        Scene selecao = new Scene(raiz, 1000, 400);
        selecao.getStylesheets().add("resources/font.css");

        FormatacaoTabelar layout = new FormatacaoTabelar(100.0, 40.0, 10);

        Arrays.stream(confDregos.dregos)
            .map((drego) -> {
                Rectangle rect = new Rectangle(100.0, 40.0);
                Label lblDrego = new Label(drego.getNome());
                lblDrego.setMinWidth(100.0);
                lblDrego.setMinHeight(40.0);
                lblDrego.setWrapText(true);
                lblDrego.setTextAlignment(TextAlignment.CENTER);

                Random rdm = new Random();
                rect.setFill(
                    Paint.valueOf("rgb("
                        + (rdm.nextInt(155) + 100)
                        + ", " + (rdm.nextInt(155) + 100)
                        + ", " + (rdm.nextInt(155) + 100)
                        + ")"));

                lblDrego.setOnMouseClicked((evt) -> {
                    try {
                        Campanha.iniciar(drego, primaryStage);
                    } catch (Exception e) {

                        new Alert(
                            Alert.AlertType.ERROR,
                            "Erro carregando campanha: " + e.getMessage())
                            .show();
                        primaryStage.close();
                    }
                });
                lblDrego.setCursor(Cursor.HAND);

                final Vec2d celula = layout.proxCelula();
                lblDrego.setTranslateX(padding + celula.x);
                lblDrego.setTranslateY(padding + celula.y);

                rect.setTranslateX(padding + celula.x);
                rect.setTranslateY(padding + celula.y);
                raiz.getChildren().add(rect);
                return lblDrego;
            })
            .forEach((lbl) -> raiz.getChildren().add(lbl));

        primaryStage.setTitle("Treta Camp - Selecionar personagem");
        primaryStage.setScene(selecao);
        primaryStage.show();
    }
}
