package br.unicamp.tretacamp;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.tretacamp.modelo.Efeito;
import br.unicamp.tretacamp.modelo.Poder;
import br.unicamp.tretacamp.modelo.TipoEfeito;

public class ConfiguracaoPoder {
	final static Poder soco = new Poder("Soco", 10.0, 5.0, null);
	
	final static Poder chute = new Poder("Chute", 12.0, 7.0, null);
	
	private final static ArrayList<Efeito> efeitosPepitaMagica = (ArrayList<Efeito>) Arrays.asList(new Efeito(TipoEfeito.ATORDOAR, 3.0, 2));
	
	final static Poder pepitaMagica = new Poder("Pepita Mágica", 23.0, 18.0, efeitosPepitaMagica);
	
	private final static ArrayList<Efeito> efeitosBolaDeFogo = (ArrayList<Efeito>) Arrays.asList(new Efeito(TipoEfeito.QUEIMAR, 3.0, 3));
	
	final static Poder bolaDeFogo = new Poder("Bola de Fogo", 14.0, 18.0, efeitosBolaDeFogo);
	
}
