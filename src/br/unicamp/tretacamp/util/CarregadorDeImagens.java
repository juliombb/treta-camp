package br.unicamp.tretacamp.util;

import br.unicamp.tretacamp.modelo.Drego;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 23/06/18
 */
public class CarregadorDeImagens {
    public static Image carregarImg(String imgStr, Stage primaryStage) {
        try {
            return new Image(
                new FileInputStream(imgStr)
            );
        } catch (Exception e) {
            new Alert(
                Alert.AlertType.ERROR,
                "Erro carregando imagem: " + e.getMessage())
                .show();
            primaryStage.close();
            return null;
        }
    }

    public static ImageView carregar(String imgStr, Stage primaryStage) {
        ImageView img;
        try {
            img = new ImageView(new Image(
                new FileInputStream(imgStr)
            ));
        } catch (Exception e) {
            new Alert(
                Alert.AlertType.ERROR,
                "Erro carregando imagem: " + e.getMessage())
                .show();
            primaryStage.close();
            return null;
        }
        return img;
    }

    public static ImageView carregar(Drego drego, Stage primaryStage) {
        return carregar(drego.getVisual(), primaryStage);
    }
}
