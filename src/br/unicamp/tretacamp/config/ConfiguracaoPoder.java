package br.unicamp.tretacamp.config;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.tretacamp.modelo.efeito.Efeito;
import br.unicamp.tretacamp.modelo.efeito.Enfraquecer;
import br.unicamp.tretacamp.modelo.efeito.Queimar;
import br.unicamp.tretacamp.modelo.Poder;

public class ConfiguracaoPoder {
	public final static Poder SOCO = new Poder("Soco", 10.0, 5.0, null);
	
	public final static Poder CHUTE = new Poder("Chute", 12.0, 7.0, null);
	
	private final static ArrayList<Efeito> EFEITOS_PEPITA_MAGICA =
		new ArrayList<>(Arrays.asList(new Enfraquecer(3.0, 2)));
	
	public final static Poder PEPITA_MAGICA = new Poder("Pepita Mágica", 23.0, 18.0, EFEITOS_PEPITA_MAGICA);
	
	private final static ArrayList<Efeito> EFEITOS_BOLA_DE_FOGO =
		new ArrayList<>(Arrays.asList(new Queimar(2.0, 3)));
	
	public final static Poder BOLA_DE_FOGO = new Poder("Bola de Fogo", 14.0, 18.0, EFEITOS_BOLA_DE_FOGO);
	
}
