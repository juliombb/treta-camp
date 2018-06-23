package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoCenario;
import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.config.ConfiguracaoEstilo;
import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Poder;
import br.unicamp.tretacamp.util.CarregadorDeImagens;
import br.unicamp.tretacamp.util.FormatacaoTabelar;
import br.unicamp.tretacamp.util.Vect2;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Random;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */

public class Campanha {
    private static MediaPlayer staticPlayer = null;
    // isso previne q o player seja coletado pelo GC

    public static void iniciar(Drego jogador, Stage primaryStage) throws Exception {
        ConfiguracaoEstilo estilo = ConfiguracaoEstilo.getInstance();
        ConfiguracaoDregos confDregos = ConfiguracaoDregos.getInstance();

        Group raiz = new Group();
        Scene campanha = new Scene(raiz);
        campanha.getStylesheets().add("resources/font.css");

        Drego inimigo = selecionarDregoInimigo(confDregos, jogador);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        redimensionarJanela(primaryStage, screenSize);

        Double widthTela = primaryStage.getWidth();
        Double heightTela = primaryStage.getHeight();

        Text txtConsole = new Text("Seu turno. Escolha uma habilidade.");

        if (!configurarCenario(primaryStage, raiz, widthTela, heightTela)) return;

        final ImageView imgJog = configurarImagemJogador(jogador, primaryStage, estilo, widthTela, heightTela);
        if (imgJog == null) return;
        Label lblJog = configurarLabelJogador(estilo, imgJog);
        Label lblVidaJog = configurarLabelVidaJog(jogador, estilo, imgJog);
        Label lblEnergiaJog = configLabelEnergiaJog(jogador, estilo, lblVidaJog);

        final ImageView imgIni = configurarImagemInimigo(primaryStage, estilo, inimigo, widthTela, heightTela);
        if (imgIni == null) return;
        Label lblIni = configurarLabelInimigo(estilo, imgIni);
        Label lblVidaIni = configurarLabelVidaIni(jogador, estilo, imgIni);
        Label lblEnergiaIni = configLabelEnergiaIni(jogador, estilo, lblVidaIni);

        raiz.getChildren().addAll(imgJog, lblJog, lblVidaJog, lblEnergiaJog,
            imgIni, lblIni, lblVidaIni, lblEnergiaIni);

        FormatacaoTabelar tab = new FormatacaoTabelar(widthTela * 0.2, heightTela * 0.15, 2, heightTela * 0.05);

        jogador.getPoderes().forEach((poder) -> {
            Tooltip tooltip = criarTooltipPoder(widthTela, poder);
            Button btn = criarBotaoPoder(primaryStage, widthTela, heightTela, poder, tooltip);
            if (btn == null) return;

            btn.getStyleClass().addAll(estilo.PIXEL_LOVE, estilo.HOVER_BUTTON);

            Vect2 pos = tab.proxCelula();
            Double pdX = imgJog.getTranslateX() + widthTela * 0.05;
            Double pdY = imgJog.getTranslateY() + imgJog.getFitHeight() + heightTela * 0.1;
            btn.setTranslateX(pdX + pos.x);
            btn.setTranslateY(pdY + pos.y);

            btn.setOnMouseClicked((evt) -> {
                poder.aplicar(jogador, inimigo);
                atualizarCoisas(lblVidaJog, lblEnergiaJog, lblVidaIni, lblEnergiaIni, jogador, inimigo);
                txtConsole.setText("Você atacou o inimigo com a habilidade " +
                    poder.getNome() +
                    " e deu " +
                    poder.getDanoInstantaneo() +
                    " de dano. Agora é a vez dele...");
                btn.setDisable(true);
            });

            raiz.getChildren().add(btn);
        });

        ImageView monitor = CarregadorDeImagens.carregar("src/resources/monitor.png", primaryStage);
        if (monitor == null) return;
        monitor.setFitWidth(widthTela * 0.5);
        monitor.setFitHeight(heightTela * 0.4);
        monitor.setX(imgIni.getX() - widthTela * 0.25);
        monitor.setY(imgIni.getY() + imgIni.getFitHeight() + heightTela * 0.025);

        raiz.getChildren().add(monitor);

        txtConsole.setWrappingWidth(monitor.getFitWidth() * 0.8);
        txtConsole.setX(monitor.getX() + monitor.getFitWidth() * 0.1);
        txtConsole.setY(monitor.getY() + monitor.getFitHeight() * 0.2);
        txtConsole.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.SOMBRA, estilo.BRANCO);
        txtConsole.setFill(Color.WHITE);

        raiz.getChildren().add(txtConsole);

        primaryStage.setTitle("Treta Camp - Campanha " + jogador.getNome());
        primaryStage.setScene(campanha);
        primaryStage.show();
    }

    private static void atualizarCoisas(Label lblVidaJog, Label lblEnergiaJog, Label lblVidaIni, Label lblEnergiaIni, Drego jogador, Drego inimigo) {
        lblVidaJog.setText("Sua vida: " + jogador.getVida());
        lblEnergiaJog.setText("Sua energia: " + jogador.getEnergia());
        lblVidaIni.setText("Vida do inimigo: " + inimigo.getVida());
        lblEnergiaIni.setText("Energia do inimigo: " + inimigo.getEnergia());
    }

    private static Label configLabelEnergiaJog(Drego jogador, ConfiguracaoEstilo estilo, Label lblVidaJog) {
        Label lblEnergiaJog = new Label("Sua energia: " + jogador.getEnergia());
        lblEnergiaJog.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblEnergiaJog.setTranslateY(lblVidaJog.getTranslateY() + lblVidaJog.getFont().getSize() * 2);
        lblEnergiaJog.setTranslateX(lblVidaJog.getTranslateX());
        return lblEnergiaJog;
    }

    private static Label configurarLabelVidaJog(Drego jogador, ConfiguracaoEstilo estilo, ImageView imgJog) {
        Label lblVidaJog = new Label("Sua vida: " + jogador.getVida());
        lblVidaJog.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblVidaJog.setTranslateY(imgJog.getY() + imgJog.getFitHeight() * 0.2);
        lblVidaJog.setTranslateX(imgJog.getX() + imgJog.getFitWidth() * 0.8);
        return lblVidaJog;
    }

    private static Label configLabelEnergiaIni(Drego inimigo, ConfiguracaoEstilo estilo, Label lblVidaIni) {
        Label lblEnergiaIni = new Label("Energia do inimigo: " + inimigo.getEnergia());
        lblEnergiaIni.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblEnergiaIni.setTranslateY(lblVidaIni.getTranslateY() + lblVidaIni.getFont().getSize() * 2);
        lblEnergiaIni.setTranslateX(lblVidaIni.getTranslateX());
        return lblEnergiaIni;
    }

    private static Label configurarLabelVidaIni(Drego inimigo, ConfiguracaoEstilo estilo, ImageView imgIni) {
        Label lblVidaIni = new Label("Vida do inimigo: " + inimigo.getVida());
        lblVidaIni.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblVidaIni.setTranslateY(imgIni.getY() + imgIni.getFitHeight() * 0.2);
        lblVidaIni.setTranslateX(imgIni.getX());
        return lblVidaIni;
    }

    private static Button criarBotaoPoder(Stage primaryStage, Double widthTela, Double heightTela, Poder poder, Tooltip tooltip) {
        Button btn = new Button(poder.getNome());
        btn.setTooltip(tooltip);

        btn.setMinWidth(widthTela * 0.2);
        btn.setMinHeight(heightTela * 0.15);
        btn.setMaxWidth(widthTela * 0.2);
        btn.setMaxHeight(heightTela * 0.15);
        btn.setTextAlignment(TextAlignment.RIGHT);
        btn.setAlignment(Pos.CENTER_RIGHT);

        Image img = CarregadorDeImagens.carregarImg(poder.getIcone(), primaryStage);
        if (img == null) return null;
        BackgroundFill[] fill = {
            new BackgroundFill(
                Paint.valueOf("rgb(220, 220, 220)"), null, null
            )
        };
        BackgroundImage[] bi = {
            new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(
                    Side.LEFT, 0.0, true,
                    Side.TOP, 0.1, true),
                new BackgroundSize(0.2, 0.8, true, true, true, false))
        };
        btn.setBackground(new Background(fill, bi));
        btn.setOnMouseExited((evt) -> tooltip.hide());
        return btn;
    }

    private static Tooltip criarTooltipPoder(Double widthTela, Poder poder) {
        Tooltip tooltip = new Tooltip();
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(widthTela * 0.4);
        tooltip.setText(
            poder.getDescricao() + System.lineSeparator() +
                "Custo: " + poder.getCusto() + System.lineSeparator() +
                "Dano: " + poder.getCusto() + System.lineSeparator() +
                "Efeitos causados: " + poder.listaEfeitos()
        );

        if (Integer.valueOf(System.getProperty("java.version").split("\\.")[0]) > 8) {
            tooltip.setShowDelay(Duration.ZERO);
            tooltip.setAutoHide(false);
            tooltip.setHideDelay(Duration.INDEFINITE);
            tooltip.setShowDuration(Duration.INDEFINITE);
            tooltip.setHideOnEscape(true);
        }
        return tooltip;
    }

    private static void redimensionarJanela(Stage primaryStage, Dimension screenSize) {
        primaryStage.setWidth(screenSize.getWidth() * 0.95);
        primaryStage.setHeight(screenSize.getHeight() * 0.95);
        primaryStage.setX(screenSize.getWidth() * 0.025);
        primaryStage.setY(screenSize.getWidth() * 0.025);
    }

    private static MediaPlayer tocarSom() {
        Media media = new Media(
            new File("src/resources/musicaLuta.mp3")
                .toURI().toString()
        );
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(0.008);
        player.play();

        return player;
    }

    private static Label configurarLabelInimigo(ConfiguracaoEstilo estilo, ImageView imgIni) {
        Label lblIni = new Label("Inimigo");
        lblIni.getStyleClass().addAll(estilo.PIXEL_LOVE, estilo.BRANCO, estilo.SOMBRA);
        lblIni.setTranslateY(imgIni.getY());
        lblIni.setTranslateX(imgIni.getX());
        return lblIni;
    }

    private static ImageView configurarImagemInimigo(Stage primaryStage, ConfiguracaoEstilo estilo, Drego inimigo, Double widthTela, Double heightTela) {
        final ImageView imgIni = CarregadorDeImagens.carregar(inimigo, primaryStage);
        if (imgIni == null) return null;

        imgIni.setFitHeight(heightTela * 0.5);
        imgIni.setFitWidth(widthTela * 0.2);
        imgIni.getStyleClass().add(estilo.IMG);
        imgIni.setX(widthTela * 0.95 - imgIni.getFitWidth());
        imgIni.setY(heightTela * 0.05);
        imgIni.setScaleX(-1.0);
        return imgIni;
    }

    private static Label configurarLabelJogador(ConfiguracaoEstilo estilo, ImageView imgJog) {
        Label lblJog = new Label("Voce");
        lblJog.getStyleClass().addAll(estilo.PIXEL_LOVE, estilo.BRANCO, estilo.SOMBRA);
        lblJog.setTranslateY(imgJog.getY());
        lblJog.setTranslateX(imgJog.getX());
        return lblJog;
    }

    private static ImageView configurarImagemJogador(Drego jogador, Stage primaryStage, ConfiguracaoEstilo estilo, Double widthTela, Double heightTela) {
        final ImageView imgJog = CarregadorDeImagens.carregar(jogador, primaryStage);
        if (imgJog == null) return null;

        imgJog.setFitHeight(heightTela * 0.5);
        imgJog.setFitWidth(widthTela * 0.2);
        imgJog.getStyleClass().add(estilo.IMG);
        imgJog.setX(widthTela * 0.05);
        imgJog.setY(heightTela * 0.05);
        return imgJog;
    }

    private static boolean configurarCenario(Stage primaryStage, Group raiz, Double widthTela, Double heightTela) {
        ImageView cenario = null;
        cenario = CarregadorDeImagens.carregar(
            ConfiguracaoCenario.cenarios[new Random().nextInt(ConfiguracaoCenario.length())],
            primaryStage);
        if (cenario == null) return false;

        cenario.setFitHeight(heightTela);
        cenario.setFitWidth(widthTela);
        cenario.setX(0);
        cenario.setY(0);
        raiz.getChildren().add(cenario);
        return true;
    }

    private static Drego selecionarDregoInimigo(ConfiguracaoDregos confDregos, Drego jogador) {
        Random rdm = new Random();
        Drego ret = jogador;
        while (ret.equals(jogador)) {
            ret = confDregos.dregos[rdm.nextInt(confDregos.dregos.length)];
        }

        Alert alerta = new Alert(
            Alert.AlertType.INFORMATION,
            "As forças malignas escolheram o drego " +
                ">>" + ret.getNome() + "<<"
                + " para te destruir. Boa Sorte!");
        ((Stage) alerta.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alerta.show();
        alerta.setOnCloseRequest((evt) -> staticPlayer = tocarSom());

        return ret;
    }
}
