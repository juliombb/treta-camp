package br.unicamp.tretacamp;

import br.unicamp.tretacamp.config.ConfiguracaoDregos;
import br.unicamp.tretacamp.modelo.Diferencial;
import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Efeito;
import br.unicamp.tretacamp.modelo.EfeitoParalizar;
import br.unicamp.tretacamp.modelo.Item;
import br.unicamp.tretacamp.modelo.PoderEspecial;
import br.unicamp.tretacamp.modelo.Tipo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;

import static br.unicamp.tretacamp.JogoOld.Perdedor.JOGADOR;
import static br.unicamp.tretacamp.JogoOld.Perdedor.INIMIGO;


public class JogoOld extends Application {

	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {      	
            final Scanner sc = new Scanner(System.in);

            Drego[] dregos = ConfiguracaoDregos.getInstance().dregos;

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

            String perdedor = null;
            
            do {
                System.out.println("Seu turno ");
                // menu do turno do jogador, mostra habilidades e deixa ele selecionar
                trataEfeito(jogador);
                if (verificarCustoMinimo(jogador)) {
                		if (!estaParalizado(jogador)) {
	                    randomItem(true);
						poderJogador(jogador, inimigo, sc);
						mostraStatus(jogador, inimigo);
                		}
                }
                else {
                		System.out.println("Jogador n tem habilidades para usar ");
                		perdedor = JOGADOR;
                		break;
                }

                System.out.println("Turno do oponente ");
                Thread.sleep(2000);
                // aleatorio
                trataEfeito(inimigo);
                if(verificarCustoMinimo(jogador)) {
                		if (!estaParalizado(inimigo)) {
	                    randomItem(false);
						poderInimigo(jogador, inimigo, sc);
						mostraStatus(jogador, inimigo);
					}
                }
                else {
	                	perdedor = INIMIGO;
	                	break;
                }
               

            } while (jogador.getVida() > 0 && inimigo.getVida() > 0);


            // definir aqui se o jogador perdeu
            if (jogador.getVida() == 0) {
                perdedor = JOGADOR;
            } else if (inimigo.getVida() == 0) {
                perdedor = INIMIGO;
            }
            
            if (perdedor.equals(JOGADOR)) {
            		System.out.println(inimigo.getControlador() + " Venceu!");
            } else {
            		System.out.println(jogador.getControlador() + " Venceu!");
            }
            System.out.println();
            System.out.println("Fim");
           

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
        		efeito.acontecer(drego);
        });

        drego.getEfeitos().removeIf(efeito -> efeito.getDuracaoEmTurnos() == 0);
    }
    
    private boolean estaParalizado(Drego drego) {
	    	for (Efeito efeito: drego.getEfeitos()) {
	    		if (efeito instanceof EfeitoParalizar) {
	    			return true;
	    		}
	    	}
	    	
	    	return false;
    }
    
    private void mostraStatus(Drego jogador1, Drego jogador2) {
    		System.out.println("---Status---");
    		System.out.println("Vida: ");
    		System.out.println("\t" + jogador1.getControlador() + ": " + jogador1.getVida());
    		System.out.println("\t" + jogador2.getControlador() + ": " + jogador2.getVida());
    		System.out.println("Energia: ");
    		System.out.println("\t" + jogador1.getControlador() + ": " + jogador1.getEnergia());
    		System.out.println("\t" + jogador2.getControlador() + ": " + jogador2.getEnergia());
    		System.out.println();
    }
    
    private boolean verificarCustoMinimo (Drego drego) {
	    	double min = 100; //valor inicial de energia(maximo)
	    	
	    	for(int i = 0; i < drego.getPoderes().size(); i++) {
	    		if(drego.getPoderes().get(i).getCusto() < min) {
	    			min = drego.getPoderes().get(i).getCusto();
	    		}
	    	}
	    	
	    	if (drego.getEnergia() > min) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
    }
    
    private void salvarEstadoJogo(Drego jogador, Drego inimigo) {
	    	String filename = "Jogo.dat";
	    	
	    	try {
	    		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream (filename));
	    		output.writeObject(jogador);
	    		output.writeObject(inimigo);
	    		output.flush();
	    		output.close();
	    	}
	    	catch(IOException ex) {
	    		ex.printStackTrace();
	    	}
    }
    
    private void carregarEstadoJogo() {
	    	String filename = "Jogo.dat";
	    	
	    	try {
	    		ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
	    		
	    		Drego d1 = (Drego) input.readObject();
	    		System.out.println(d1);
	    		System.out.println();
	    		
	    		Drego d2 = (Drego) input.readObject();
	    		System.out.println(d2);
	    		System.out.println();
	    		
	    		input.close();
	    	}
	    	catch (EOFException endOfFileException) {
	    		endOfFileException.printStackTrace();
	    	}
	    	catch (ClassNotFoundException classNotFoundException) {
	    		classNotFoundException.printStackTrace();
	    	}
	    	catch(IOException ex) {
	    		ex.printStackTrace();
	    	}
    }


    private void limparConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println("\n");
        }
        System.out.flush();
    }

    private Item randomItem(boolean showMessages) {

        Random random = new Random();

        int numero = random.nextInt(30);

        switch (numero) {
            case 0: {
                if (showMessages) {
                    System.out.println("Um pimpolho muito simpático apareceu e te entregou um frasco... ");
                    System.out.println("No vidro um escrito diz: Poção de Cura... será que é confiável?");
                }
                return new Item("Poção de Cura", "");
            }
            case 10: {
                if (showMessages) {
                    System.out.println("Um búfalo alado deixou cair ao seu lado uma garrafa... ");
                    System.out.println("Você se lembra das aulas de arqueologia que antigos escritos ");
                    System.out.print("já relataram de um búfalo que voava levando energia para a populaçao.");
                }
                return new Item("Blue Horse", "");
            }
            case 20: {
                if (showMessages) {
                    System.out.println("Alô Alô, Você sabe quem eu sou? Isso mesmo, a pantera da perdição. Miaaaaw");
                    System.out.println("É como diz aquele ditado né? Fique com esse frasco!");
                    System.out.println("Não há nada escrito no frasco.");
                }
                return new Item("Frasco Misterioso", "");

            }
            default: return null;
        }
    }

    static class Perdedor {
        static final String JOGADOR = "jogador";
        static final String INIMIGO = "inimigo";
    }
}
