package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.config.ConfiguracaoEstilo;
import br.unicamp.tretacamp.util.FormatacaoTabelar;
import com.sun.javafx.geom.Vec2d;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.util.Arrays;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */
public class SelecaoDePersonagens {

    private static final Double PADDING_Y = 80.0;
    private static final Double PADDING_X = 140.0;

    public static void iniciar(Stage primaryStage) throws Exception {
        ConfiguracaoEstilo fontes = ConfiguracaoEstilo.getInstance();
        ConfiguracaoDregos confDregos = ConfiguracaoDregos.getInstance();

        Group raiz = new Group();
        Scene selecao = new Scene(raiz, 1000, 400);
        selecao.getStylesheets().add("resources/font.css");

        FormatacaoTabelar layout = new FormatacaoTabelar(
            140.0, 120.0, 4, 10.0);

        Arrays.stream(confDregos.dregos)
            .forEach((drego) -> {
                Label lblDrego = new Label(drego.getNome());
                lblDrego.setMinWidth(140.0);
                lblDrego.setMinHeight(120.0);
                lblDrego.setWrapText(true);
                lblDrego.setAlignment(Pos.BOTTOM_CENTER);
                lblDrego.getStyleClass().add(fontes.PIXEL_LOVE);
                lblDrego.getStyleClass().add(fontes.HOVER_TEXT);
                lblDrego.setCursor(Cursor.HAND);

                ImageView imgDrego = null;
                try {
                    imgDrego = new ImageView(new Image(
                        new FileInputStream(drego.getVisual())
                    ));
                } catch (Exception e) {
                    new Alert(
                        Alert.AlertType.ERROR,
                        "Erro carregando imagem do drego: " + e.getMessage())
                        .show();
                    primaryStage.close();
                    return;
                }
                imgDrego.setFitHeight(100.0);
                imgDrego.setFitWidth(130.0);
                imgDrego.getStyleClass().add(fontes.HOVER_TEXT);
                imgDrego.setCursor(Cursor.HAND);

                EventHandler<MouseEvent> mouseClicked = event -> {
                    try {
                        Campanha.iniciar(drego, primaryStage);
                    } catch (Exception e) {

                        new Alert(
                            Alert.AlertType.ERROR,
                            "Erro carregando campanha: " + e.getMessage())
                            .show();
                        primaryStage.close();
                    }
                };
                lblDrego.setOnMouseClicked(mouseClicked);
                imgDrego.setOnMouseClicked(mouseClicked);

                final Vec2d celula = layout.proxCelula();
                lblDrego.setTranslateX(PADDING_X + celula.x);
                lblDrego.setTranslateY(PADDING_Y + celula.y);

                imgDrego.setTranslateX(PADDING_X + celula.x);
                imgDrego.setTranslateY(PADDING_Y + celula.y);

                raiz.getChildren().add(imgDrego);
                raiz.getChildren().add(lblDrego);
            });


        Label lblTitulo = new Label("Selecione seu drego");
        lblTitulo.getStyleClass().add(fontes.PIXEL_LOVE_BIG);

        lblTitulo.setTranslateX(300);
        lblTitulo.setTranslateY(20);

        raiz.getChildren().add(lblTitulo);

        primaryStage.setWidth(900);
        primaryStage.setHeight(500);
        primaryStage.setTitle("Treta Camp - Selecionar personagem");
        primaryStage.setScene(selecao);
        primaryStage.show();
    }
}
