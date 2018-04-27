package br.unicamp.tretacamp;

import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.PoderEspecial;
import br.unicamp.tretacamp.modelo.Tipo;
import br.unicamp.tretacamp.config.ConfiguracaoPoder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

import static br.unicamp.tretacamp.Jogo.Perdedor.JOGADOR;

public class Jogo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            final Scanner sc = new Scanner(System.in);

            final Drego mago = new Drego(
                "mago", "mago.png", 100.0, 100.0, Tipo.AGUA, null);
            final Drego espotenique = new Drego(
                "espotenique", "espotenique.png", 100.0, 100.0, Tipo.AGUA, null);
            final Drego monho = new Drego(
                "monho", "monho.gif", 100.0, 100.0, Tipo.AGUA, null);
            final Drego cobroso = new Drego(
                "cobroso", "cobroso.png", 100.0, 100.0, Tipo.AGUA, null);

            mago.adicionarPoder(ConfiguracaoPoder.PEPITA_MAGICA);
            espotenique.adicionarPoder(ConfiguracaoPoder.CHUTE);
            monho.adicionarPoder(ConfiguracaoPoder.SOCO);
            cobroso.adicionarPoder(ConfiguracaoPoder.BOLA_DE_FOGO);

            final Drego[] dregos = {mago, espotenique, monho, cobroso};

            final Drego jogador =
                dregos[selecionaDrego("jogador", sc)].clonarPara("jogador");
            limparConsole();
            System.out.println("Drego escolhido para o jogador: " + jogador.getNome());
            Thread.sleep(1000);
            limparConsole();

            final Drego inimigo =
                dregos[selecionaDrego("inimigo", sc)].clonarPara("inimigo");
            limparConsole();
            System.out.println("Drego escolhido para o inimigo: " + inimigo.getNome());
            Thread.sleep(1000);
            limparConsole();

            System.out.println("Inicio da batalha");

            do {
                System.out.println("Seu turno ");
                // menu do turno do jogador, mostra habilidades e deixa ele selecionar
                trataEfeito(jogador);
                poderJogador(jogador, inimigo, sc);
                System.out.println("Sua vida: " + jogador.getVida());
                System.out.println("Sua energia: " + jogador.getEnergia());
                System.out.println();

                System.out.println("Turno do oponente ");
                // aleatorio
                trataEfeito(inimigo);
                poderInimigo(jogador, inimigo, sc);
                System.out.println("Sua Vida: " + inimigo.getVida());
                System.out.println("Sua Energia: " + inimigo.getEnergia());
                System.out.println();

            } while (jogador.getVida() > 0 && inimigo.getVida() > 0);

            System.out.println("Fim");

            //definir aqui se o jogador perdeu
            final String perdedor = JOGADOR;

            // Aqui ficaria a parte visual
            // para a primeira entrega, essa parte esta
            // limitada e a batalha acontece na linha de comando.
            // por enquanto so mostramos as imagens
            {
                ImageView imagemJog = new ImageView(
                    "resources/" + jogador.getVisual());
                ImageView imagemIni = new ImageView(
                    "resources/" + inimigo.getVisual());

                StackPane raiz = new StackPane();
                Scene cena = new Scene(raiz, 1000, 400);
                imagemJog.setTranslateX(-400);
                imagemIni.setTranslateX(400);
                if (perdedor == JOGADOR) {
                    imagemJog.setRotate(90.0);
                } else {
                    imagemIni.setRotate(90.0);
                }
                raiz.getChildren().add(imagemJog);
                raiz.getChildren().add(imagemIni);
                primaryStage.setTitle("Treta Camp - imagem da batalha");
                primaryStage.setScene(cena);
                primaryStage.show();
            }
            // fim da parte visual

        } catch (Exception e) {
            System.out.println("Problema inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int selecionaDrego(String entidade, Scanner sc) {
        System.out.println("Selecione o drego que deseja para o " + entidade);
        while (true) {
            System.out.println("1. Mago");
            System.out.println();
            System.out.println("2. Espotenique");
            System.out.println();
            System.out.println("3. Monho");
            System.out.println();
            System.out.println("4. Cobroso");
            System.out.println();

            try {
                final int opcao = sc.nextInt();
                if (opcao < 0 || opcao > 4) {
                    limparConsole();
                    System.out.println("Opcao invalida. Tente novamente...");
                    continue;
                }

                return opcao - 1; // opcao vai ser a posicao no array

            } catch (Exception e) {
                limparConsole();
                System.out.println("Problema na leitura. Tente novamente...");
            }
        }
    }

    private void poderJogador(Drego jogador, Drego inimigo, Scanner sc) {
        int j = 1;
        String out;
        System.out.println("Selecione o poder que deseja aplicar: ");
        for (int i = 0; i < jogador.getPoderes().size(); i++) {
        	    out = "";
        	    out += j + ". " + jogador.getPoderes().get(i).getNome() +
                "Custo: " + jogador.getPoderes().get(i).getCusto();
            if (jogador.getPoderes().get(i) instanceof PoderEspecial) {
                out += "*";
            }
            j++;
            System.out.println(out);
        }
        int opcao = sc.nextInt();
        while (opcao < 1 || opcao > jogador.getPoderes().size()) {
            System.out.println("Opcao invalida. Tente novamente...");
            opcao = sc.nextInt();
        }
        while (jogador.getPoderes().get(opcao - 1).getCusto() > jogador.getEnergia()) {
            System.out.println("Custo de energia muito alto. Selecione outro poder...");
            opcao = sc.nextInt();
        }
        jogador.getPoderes().get(opcao - 1).aplicar(jogador, inimigo);
    }

    private void poderInimigo(Drego jogador, Drego inimigo, Scanner sc) {
        Random random = new Random();
        int i = random.nextInt(inimigo.getPoderes().size());

        // seleciona poder que condiz com a quantidade de energia que o drego possui
        while (inimigo.getEnergia() < inimigo.getPoderes().get(i).getCusto()) {
            i = random.nextInt(inimigo.getPoderes().size());
        }

        inimigo.getPoderes().get(i).aplicar(inimigo, jogador);
        System.out.println("O inimigo selecionou o poder " + inimigo.getPoderes().get(i).getNome());
    }

    private void trataEfeito(Drego drego) {
        drego.getEfeitos().forEach((efeito) -> {

            switch (efeito.getTipoEfeito()) {

                case QUEIMAR:
                    if (efeito.reduzirDuracaoEmTurnos()) {
                        drego.diminuirVida(efeito.getValor());
                    }
                    break;

                case ENFRAQUECER:
                    if (efeito.reduzirDuracaoEmTurnos()) {
                        double novaEnergia = drego.getEnergia() - efeito.getValor();

                        drego.setEnergia(novaEnergia > 0 ? novaEnergia : 0);
                    }
                    break;

                case PARALIZAR:
                    efeito.reduzirDuracaoEmTurnos();
                    break;

                default:
                    break;
            }
        });

        drego.getEfeitos().removeIf(efeito -> efeito.getDuracaoEmTurnos() == 0);
    }


    private void limparConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println("\n");
        }
        System.out.flush();
    }

    static class Perdedor {
        static final String JOGADOR = "jog";
        static final String INIMIGO = "ini";
    }
}
