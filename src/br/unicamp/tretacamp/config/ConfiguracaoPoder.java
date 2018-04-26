package br.unicamp.tretacamp.config;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.tretacamp.modelo.Efeito;
import br.unicamp.tretacamp.modelo.Poder;
import br.unicamp.tretacamp.modelo.TipoEfeito;

public class ConfiguracaoPoder {
	final static Poder SOCO = new Poder("Soco", 10.0, 5.0, null);
	
	final static Poder CHUTE = new Poder("Chute", 12.0, 7.0, null);
	
	private final static ArrayList<Efeito> EFEITOS_PEPITA_MAGICA =
		new ArrayList<>(Arrays.asList(new Efeito(TipoEfeito.ENFRAQUECER, 3.0, 2)));
	
	final static Poder PEPITA_MAGICA = new Poder("Pepita Mágica", 23.0, 18.0, EFEITOS_PEPITA_MAGICA);
	
	private final static ArrayList<Efeito> EFEITOS_BOLA_DE_FOGO =
		new ArrayList<>(Arrays.asList(new Efeito(TipoEfeito.QUEIMAR, 3.0, 3)));
	
	final static Poder BOLA_DE_FOGO = new Poder("Bola de Fogo", 14.0, 18.0, EFEITOS_BOLA_DE_FOGO);
	
}
