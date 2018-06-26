package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoCenario;
import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.config.ConfiguracaoEstilo;
import br.unicamp.tretacamp.config.ConfiguracaoItem;
import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Item;
import br.unicamp.tretacamp.modelo.Poder;
import br.unicamp.tretacamp.modelo.efeito.Efeito;
import br.unicamp.tretacamp.modelo.efeito.Enfraquecer;
import br.unicamp.tretacamp.modelo.efeito.Queimar;
import br.unicamp.tretacamp.util.CarregadorDeImagens;
import br.unicamp.tretacamp.util.FormatacaoTabelar;
import br.unicamp.tretacamp.util.Vect2;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Júlio Moreira Blás de Barros (julio.barros@movile.com)
 * @since 22/06/18
 */

public class Campanha {
    private static MediaPlayer staticPlayer = null;
    // isso previne q o player seja coletado pelo GC

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void iniciar(Drego jogador, Drego inimigoAnterior, Stage primaryStage) throws Exception {
        iniciar(jogador, inimigoAnterior, primaryStage, 1);
    }

    public static void iniciar(Drego jogador, Drego inimigoAnterior, Stage primaryStage, int fase) throws Exception {
        ConfiguracaoEstilo estilo = ConfiguracaoEstilo.getInstance();
        ConfiguracaoDregos confDregos = ConfiguracaoDregos.getInstance();

        Group raiz = new Group();
        Scene campanha = new Scene(raiz);
        campanha.getStylesheets().add("resources/font.css");
       
        if (inimigoAnterior == null) {
        	inimigoAnterior = selecionarDregoInimigoETocarSom(confDregos, jogador, fase);
        } else {
            tocarSom();
        }
        
        final Drego inimigo = inimigoAnterior;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        redimensionarJanela(primaryStage, screenSize);

        Double widthTela = primaryStage.getWidth();
        Double heightTela = primaryStage.getHeight();

        if (!configurarCenario(primaryStage, raiz, widthTela, heightTela)) return;

        final ImageView imgJog = configurarImagemJogador(jogador, primaryStage, estilo, widthTela, heightTela);
        if (imgJog == null) return;
        Label lblJog = configurarLabelJogador(estilo, imgJog, jogador);
        Label lblVidaJog = configurarLabelVidaJog(jogador, estilo, imgJog);
        Label lblEnergiaJog = configLabelEnergiaJog(jogador, estilo, lblVidaJog);

        final ImageView imgIni = configurarImagemInimigo(primaryStage, estilo, inimigo, widthTela, heightTela);
        if (imgIni == null) return;
        Label lblIni = configurarLabelInimigo(estilo, imgIni, inimigo);
        Label lblVidaIni = configurarLabelVidaIni(inimigo, estilo, imgIni);
        Label lblEnergiaIni = configLabelEnergiaIni(inimigo, estilo, lblVidaIni);

        raiz.getChildren().addAll(imgJog, lblJog, lblVidaJog, lblEnergiaJog,
            imgIni, lblIni, lblVidaIni, lblEnergiaIni);

        FormatacaoTabelar tab = new FormatacaoTabelar(widthTela * 0.2, heightTela * 0.15, 2, heightTela * 0.05);

        Text txtConsole = new Text();
        HashMap<Poder, Button> mapPoderBotao = new HashMap<>();
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
            mapPoderBotao.put(poder, btn);

            btn.setOnMouseClicked((evt) -> {
                Poder.ResultadoPoder res = poder.aplicar(jogador, inimigo);
                atualizarCoisas(lblVidaJog, lblEnergiaJog, lblVidaIni, lblEnergiaIni, jogador, inimigo);
                txtConsole.setText(res.getDesc());

                if (verificaFim(jogador, inimigo, txtConsole, imgJog, imgIni, mapPoderBotao, primaryStage, fase, raiz)) return;

                if (res.foiAplicado()) {
                    animacaozinha(imgIni);
                    txtConsole.setText(txtConsole.getText() + System.lineSeparator() + "Agora é a vez do inimigo..." + System.lineSeparator());
                    desabilitaBotoes(mapPoderBotao);

                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                    executor.schedule(() -> {
                        Platform.runLater(() -> {
                            trataEfeito(inimigo, imgIni, txtConsole);
                            vezDoInimigo(jogador, inimigo, txtConsole);
                            atualizarCoisas(lblVidaJog, lblEnergiaJog, lblVidaIni, lblEnergiaIni, jogador, inimigo);
                            if (verificaFim(jogador, inimigo, txtConsole, imgJog, imgIni, mapPoderBotao, primaryStage, fase, raiz)) return;

                            animacaozinha(imgJog);
                            trataEfeito(jogador, imgJog, txtConsole);
                            txtConsole.setText(
                                txtConsole.getText()
                                + System.lineSeparator()
                                + "Agora é sua vez...");
                            limpaEfeitos(inimigo, jogador);
                            habilitaBotoes(mapPoderBotao);
                        });
                        executor.shutdownNow();
                    },
                    1, TimeUnit.SECONDS);
                }
            });

            raiz.getChildren().add(btn);
        });

        // no caso de ter se iniciado um game acabado
        if (!verificaFim(jogador, inimigo, txtConsole, imgJog, imgIni, mapPoderBotao, primaryStage, fase, raiz)) {
            txtConsole.setText("Seu turno. Escolha uma habilidade.");
        }

        ImageView monitor = carregarMonitor(primaryStage, widthTela, heightTela, imgIni);
        if (monitor == null) return;

        raiz.getChildren().add(monitor);

        txtConsole.setWrappingWidth(monitor.getFitWidth() * 0.8);
        txtConsole.setX(monitor.getX() + monitor.getFitWidth() * 0.1);
        txtConsole.setY(monitor.getY() + monitor.getFitHeight() * 0.2);
        txtConsole.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.SOMBRA, estilo.BRANCO);
        txtConsole.setFill(Color.WHITE);

        raiz.getChildren().add(txtConsole);

        if (!carregarMochila(jogador, primaryStage, raiz, widthTela, heightTela)) return;

        primaryStage.setTitle("Treta Camp - Campanha " + jogador.getNome() + " | Fase " + fase);
        primaryStage.setScene(campanha);
        primaryStage.show();
        
        primaryStage.setOnHiding(t -> {
            promptSave(jogador, primaryStage, inimigo, fase);
        });
    }

    private static boolean carregarMochila(Drego jogador, Stage primaryStage, Group raiz, Double widthTela, Double heightTela) {
        ImageView mochila = CarregadorDeImagens.carregar("src/resources/mochila.png", primaryStage);
        if (mochila == null) return false;
        mochila.setFitHeight(heightTela * 0.1);
        mochila.setFitWidth(widthTela * 0.1);
        mochila.setY(heightTela * 0.85);
        mochila.setX(widthTela * 0.85);
        mochila.setOnMouseClicked((evt) -> {
            FormatacaoTabelar ftInv = new FormatacaoTabelar(
                    widthTela * 0.05, heightTela *0.05, 8, widthTela*0.05);

            Group raizItens = new Group();
            Scene secondScene = new Scene(raizItens);
            Stage secStage = new Stage();
            secStage.setTitle("Inventário");
            secStage.setScene(secondScene);
            secStage.setX(primaryStage.getX() + widthTela*0.025);
            secStage.setY(primaryStage.getY() + heightTela*0.025);
            secStage.setHeight(heightTela *0.9);
            secStage.setWidth(widthTela *0.9);
            jogador.getItens().forEach((item) -> {
                Tooltip ttpItem = new Tooltip(item.getNome());
                ImageView imgItem = CarregadorDeImagens.carregar(item.getImagem(), primaryStage);
                if (imgItem == null) return;
                imgItem.setFitHeight(heightTela *0.05);
                imgItem.setFitWidth(widthTela * 0.05);
                Vect2 pos = ftInv.proxCelula();
                imgItem.setTranslateX(widthTela * 0.05 + pos.x);
                imgItem.setTranslateY(heightTela *0.05 + pos.y);
                Tooltip.install(imgItem, ttpItem);
                raizItens.getChildren().add(imgItem);
            });

            secStage.show();
        });
        mochila.setCursor(Cursor.HAND);
        raiz.getChildren().add(mochila);
        return true;
    }

    private static void limpaEfeitos(Drego inimigo, Drego jogador) {
        inimigo.getEfeitos().removeIf((eft) -> eft.getDuracaoEmTurnos() <= 0);
        jogador.getEfeitos().removeIf((eft) -> eft.getDuracaoEmTurnos() <= 0);
    }

    private static void promptSave(Drego jogador, Stage primaryStage, Drego inimigo, int fase) {
        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não");
        Alert alerta = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Quer salvar o jogo?", sim, nao);
        alerta.setHeaderText("Você está saindo de um jogo não salvo.");

        alerta.initStyle(StageStyle.UNDECORATED);
        alerta.showAndWait().ifPresent(response -> {
            if (response == sim) {
                Persistencia persistencia = new Persistencia();
    			System.out.print(fase);
                persistencia.salvarEstadoJogo(jogador, inimigo, fase);
                alerta.close();
                primaryStage.close();
            } else {
                alerta.close();
                primaryStage.close();
            }
        });
    }

    private static void promptProxFase(Drego jogador, Stage primaryStage, int faseAtual, Group raiz) {
        if (jogador.getVida() <= 0.0) {
            return;
        }

        ButtonType sim = new ButtonType("Sim");
        ButtonType nao = new ButtonType("Não");
        Alert alerta = new Alert(
            Alert.AlertType.CONFIRMATION,
            "Quer ir para a próxima fase?", sim, nao);
        alerta.setHeaderText("Você venceu!");

        alerta.initStyle(StageStyle.UNDECORATED);
        alerta.showAndWait().ifPresent(response -> {
            if (response == sim) {
                alerta.close();
                try {
                    raiz.getChildren().clear();
                    staticPlayer.pause();
                    droparItem(jogador, primaryStage, faseAtual);
                    iniciar(jogador, null, primaryStage, faseAtual + 1);
                } catch (Exception e) {
                    new Alert(
                        Alert.AlertType.ERROR,
                        "Erro carregando campanha: " + e.getMessage())
                        .show();
                    e.printStackTrace();
                    primaryStage.close();
                }
            }
        });
    }

    private static void droparItem(Drego jogador, Stage primaryStage, int faseAtual) {
        Random random = new Random();

        int numero = random.nextInt(5);
        String texto = "";

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText("Você dropou um item!!");

        switch (numero) {
            case 0: {
                texto += "Um pimpolho muito simpático apareceu e te entregou um frasco... " + System.lineSeparator();
                texto += "No vidro um escrito diz: Poção de Cura... será que é confiável?" + System.lineSeparator();
                jogador.adicionarItem(ConfiguracaoItem.POCAO_VIDA);
                break;
            }
            case 1: {
                texto += "Um búfalo alado deixou cair ao seu lado uma garrafa... " + System.lineSeparator();
                texto += "Você se lembra das aulas de arqueologia que antigos escritos ";
                texto += "já relataram de um búfalo que voava levando energia para a populaçao.";
                jogador.adicionarItem(ConfiguracaoItem.POCAO_MANA);
                break;
            }
            case 3: {
                texto += "Alô Alô, Você sabe quem eu sou? Isso mesmo, a pantera daperdição. Miaaaaw" + System.lineSeparator();
                texto += "É como diz aquele ditado né? Fique com esse frasco!" + System.lineSeparator();
                texto += "Não há nada escrito no frasco." + System.lineSeparator();
                jogador.adicionarItem(ConfiguracaoItem.BLUE_HORSE);
                break;
            }
            default:
                texto += "Nada foi dropado :(";
                alerta.setHeaderText("Que triste!");
                break;
        }

        alerta.setContentText(texto);
        ((Stage) alerta.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alerta.showAndWait();
    }

    private static void trataEfeito(Drego drego, ImageView img, Text txtConsole) {
    		for (Efeito efeito: drego.getEfeitos()) {
    			if (efeito instanceof Queimar) {
    				efeito.acontecer(drego);
    				txtConsole.setText(txtConsole.getText() + System.lineSeparator() +
    						drego.getNome() + " está queimando e perde " + efeito.getValor() + " de vida!");
    			}
    			
    			if (efeito instanceof Enfraquecer) {
    				efeito.acontecer(drego);
    				txtConsole.setText(txtConsole.getText() + System.lineSeparator() +
    						drego.getNome() + " está sofrendo as consequências do enfraquecer, e perde "
    						+ efeito.getValor() + " de energia!");
    				
    			}
    		}
    };

    private static void animacaozinha(ImageView img) {
        RotateTransition ft = new RotateTransition();
        ft.setNode(img);
        ft.setDuration(new Duration(100));
        ft.setFromAngle(0.0);
        ft.setToAngle(45.0);
        ft.setCycleCount(6);
        ft.setAutoReverse(true);
        ft.play();

        RotateTransition ft2 = new RotateTransition();
        ft.setNode(img);
        ft.setDuration(new Duration(100));
        ft.setFromAngle(45.0);
        ft.setToAngle(-45.0);
        ft.setCycleCount(12);
        ft.setAutoReverse(true);
        ft.play();

        RotateTransition ft3 = new RotateTransition();
        ft.setNode(img);
        ft.setDuration(new Duration(100));
        ft.setFromAngle(-45.0);
        ft.setToAngle(45.0);
        ft.setCycleCount(12);
        ft.setAutoReverse(true);
        ft.play();

        RotateTransition ft4 = new RotateTransition();
        ft.setNode(img);
        ft.setDuration(new Duration(100));
        ft.setFromAngle(45.0);
        ft.setToAngle(0.0);
        ft.setCycleCount(6);
        ft.setAutoReverse(true);
        ft.play();
    }

    private static boolean verificaFim(Drego jogador, Drego inimigo,
                                       Text txtConsole, ImageView imgJog, ImageView imgIni,
                                       HashMap<Poder, Button> mapPoderBotao,
                                       Stage primaryStage, int faseAtual, Group raiz) {
        if (jogador.getVida() == 0) {
            txtConsole.setText(txtConsole.getText() + System.lineSeparator() + "O jogador está morto. Fim de jogo ;--;");
            desabilitaBotoes(mapPoderBotao);
            imgJog.setRotate(-90.0);
            imgJog.setY(imgJog.getY() + (imgJog.getFitHeight() - imgJog.getFitWidth()));
            return true;
        }

        if (inimigo.getVida() == 0) {
            txtConsole.setText(txtConsole.getText() + System.lineSeparator() + "O inimigo está morto. Jogador venceu!!!");
            desabilitaBotoes(mapPoderBotao);
            imgIni.setRotate(90.0);
            imgIni.setY(imgIni.getY() + (imgIni.getFitHeight() - imgIni.getFitWidth()));
            promptProxFase(jogador, primaryStage, faseAtual, raiz);
            return true;
        }
        return false;
    }

    private static ImageView carregarMonitor(Stage primaryStage, Double widthTela, Double heightTela, ImageView imgIni) {
        ImageView monitor = CarregadorDeImagens.carregar("src/resources/monitor.png", primaryStage);
        if (monitor == null) { return null; }

        monitor.setFitWidth(widthTela * 0.5);
        monitor.setFitHeight(heightTela * 0.4);
        monitor.setX(imgIni.getX() - widthTela * 0.25);
        monitor.setY(imgIni.getY() + imgIni.getFitHeight() + heightTela * 0.025);
        return monitor;
    }

    private static void vezDoInimigo(Drego jogador, Drego inimigo, Text txtConsole) {
        Random rdm = new Random();
        Poder poder = inimigo.getPoderes().get(rdm.nextInt(inimigo.getPoderes().size()));
        Poder.ResultadoPoder res = poder.aplicar(inimigo, jogador);

        while (!res.foiAplicado()) {
            poder = inimigo.getPoderes().get(rdm.nextInt(inimigo.getPoderes().size()));
            res = poder.aplicar(inimigo, jogador);
        }

        txtConsole.setText(txtConsole.getText() + System.lineSeparator()
            + res.getDesc());
    }

    private static void desabilitaBotoes(HashMap<Poder, Button> mapPoderBotao) {
        mapPoderBotao.values().forEach((btn) -> btn.setDisable(true));
    }

    private static void habilitaBotoes(HashMap<Poder, Button> mapPoderBotao) {
        mapPoderBotao.values().forEach((btn) -> btn.setDisable(false));
    }

    private static void atualizarCoisas(Label lblVidaJog, Label lblEnergiaJog, Label lblVidaIni, Label lblEnergiaIni, Drego jogador, Drego inimigo) {
        lblVidaJog.setText("Sua vida: " + df.format(jogador.getVida()));
        lblEnergiaJog.setText("Sua energia: " + df.format(jogador.getEnergia()));
        lblVidaIni.setText("Vida do inimigo: " + df.format(inimigo.getVida()));
        lblEnergiaIni.setText("Energia do inimigo: " + df.format(inimigo.getEnergia()));
    }

    private static Label configLabelEnergiaJog(Drego jogador, ConfiguracaoEstilo estilo, Label lblVidaJog) {
        Label lblEnergiaJog = new Label("Sua energia: " + df.format(jogador.getEnergia()));
        lblEnergiaJog.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblEnergiaJog.setTranslateY(lblVidaJog.getTranslateY() + lblVidaJog.getFont().getSize() * 2);
        lblEnergiaJog.setTranslateX(lblVidaJog.getTranslateX());
        return lblEnergiaJog;
    }

    private static Label configurarLabelVidaJog(Drego jogador, ConfiguracaoEstilo estilo, ImageView imgJog) {
        Label lblVidaJog = new Label("Sua vida: " + df.format(jogador.getVida()));
        lblVidaJog.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblVidaJog.setTranslateY(imgJog.getY() + imgJog.getFitHeight() * 0.2);
        lblVidaJog.setTranslateX(imgJog.getX() + imgJog.getFitWidth() * 0.8);
        return lblVidaJog;
    }

    private static Label configLabelEnergiaIni(Drego inimigo, ConfiguracaoEstilo estilo, Label lblVidaIni) {
        Label lblEnergiaIni = new Label("Energia do inimigo: " + df.format(inimigo.getEnergia()));
        lblEnergiaIni.getStyleClass().addAll(estilo.CONSOLAS_BOLD, estilo.BRANCO, estilo.SOMBRA);
        lblEnergiaIni.setTranslateY(lblVidaIni.getTranslateY() + lblVidaIni.getFont().getSize() * 2);
        lblEnergiaIni.setTranslateX(lblVidaIni.getTranslateX());
        return lblEnergiaIni;
    }

    private static Label configurarLabelVidaIni(Drego inimigo, ConfiguracaoEstilo estilo, ImageView imgIni) {
        Label lblVidaIni = new Label("Vida do inimigo: " + df.format(inimigo.getVida()));
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
                "Dano: " + poder.getDanoInstantaneo() + System.lineSeparator() +
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

    private static void tocarSom() {
        if (staticPlayer != null) {
            staticPlayer.play();
            return;
        }

        Media media = new Media(
            new File("src/resources/musicaLuta.mp3")
                .toURI().toString()
        );
        MediaPlayer player = new MediaPlayer(media);
        player.setVolume(0.008);
        player.play();

        staticPlayer = player;
    }

    private static Label configurarLabelInimigo(ConfiguracaoEstilo estilo, ImageView imgIni, Drego inimigo) {
        Label lblIni = new Label("Inimigo (" + inimigo.getNome() + ")");
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

    private static Label configurarLabelJogador(ConfiguracaoEstilo estilo, ImageView imgJog, Drego jogador) {
        Label lblJog = new Label("Voce (" + jogador.getNome() +  ")");
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

    private static Drego selecionarDregoInimigoETocarSom(ConfiguracaoDregos confDregos, Drego jogador, int fase) {
        Random rdm = new Random();
        Drego ret = jogador;
        Drego[] dregos = confDregos.getDregos(fase);
        while (ret.getNome().equals(jogador.getNome())) {
            ret = dregos[rdm.nextInt(dregos.length)].clone();
        }

        Alert alerta = new Alert(
            Alert.AlertType.INFORMATION,
            "As forças malignas escolheram o drego " +
                ">>" + ret.getNome() + "<<" + System.lineSeparator()
                + " para te destruir. Boa Sorte!");
        alerta.setWidth(800);
        ((Stage) alerta.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        alerta.show();
        alerta.setOnCloseRequest((evt) -> tocarSom());

        return ret;
    }
}
