package br.unicamp.tretacamp;/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 17/04/18
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Jogo extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    StackPane raiz = new StackPane();
    Label lblMensagem = new Label();
    Button botao = new Button();

    lblMensagem.setTranslateX(0.0);
    lblMensagem.setText("Estou aprendendo JavaFX!");
    botao.setDefaultButton(true);
    botao.setText("booora");
    botao.setOnAction(event -> {
      System.out.println("clicou");
    });
    botao.setTranslateY(30.0);
    raiz.getChildren().add(lblMensagem);
    raiz.getChildren().add(botao);

    Scene cena = new Scene(raiz, 250, 100);
    primaryStage.setTitle("Aprendendo JavaFX");
    primaryStage.setScene(cena);
    primaryStage.show();
  }
}
