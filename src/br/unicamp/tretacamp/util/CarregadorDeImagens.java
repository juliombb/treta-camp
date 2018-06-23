package br.unicamp.tretacamp.util;

import br.unicamp.tretacamp.modelo.Drego;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 23/06/18
 */
public class CarregadorDeImagens {
    public static ImageView carregar(Drego drego, Stage primaryStage) {
        ImageView imgJog;
        try {
            imgJog = new ImageView(new Image(
                new FileInputStream(drego.getVisual())
            ));
        } catch (Exception e) {
            new Alert(
                Alert.AlertType.ERROR,
                "Erro carregando imagem do drego: " + e.getMessage())
                .show();
            primaryStage.close();
            return null;
        }
        return imgJog;
    }
}
