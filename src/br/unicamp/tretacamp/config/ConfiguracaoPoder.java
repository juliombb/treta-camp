package br.unicamp.tretacamp.config;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.tretacamp.modelo.efeito.Efeito;
import br.unicamp.tretacamp.modelo.efeito.Enfraquecer;
import br.unicamp.tretacamp.modelo.efeito.Queimar;
import br.unicamp.tretacamp.modelo.Poder;

public class ConfiguracaoPoder {
	public final static Poder SOCO = new Poder(
		"Soco",
		"Um simples socão na boca",
		"src/resources/soco.png",
		10.0,
		5.0,
		null);
	
	public final static Poder CHUTE = new Poder(
		"Chute",
		"O Roundhouse Kick do agreste",
		"src/resources/chute.png",
		12.0,
		7.0,
		null);
	
	private final static ArrayList<Efeito> EFEITOS_PEPITA_MAGICA =
		new ArrayList<>(Arrays.asList(new Enfraquecer(3.0,
			2)));
	
	public final static Poder PEPITA_MAGICA = new Poder(
		"Pepita Mágica",
		"Uma pedra explosiva que causa dano mágico e enfraquece o atingido",
		"src/resources/pepita_magica.png",
		23.0,
		18.0,
		EFEITOS_PEPITA_MAGICA);
	
	private final static ArrayList<Efeito> EFEITOS_BOLA_DE_FOGO =
		new ArrayList<>(Arrays.asList(new Queimar(2.0,
			3)));
	
	public final static Poder BOLA_DE_FOGO = new Poder(
		"Bola de Fogo",
		"Exatamente o que você esperaria desse poder",
		"src/resources/bola_de_fogo.png",
		14.0,
		18.0,
		EFEITOS_BOLA_DE_FOGO);

	public final static Poder RAIO = new Poder(
		"Raio",
		"Golpe elétrico com alto poder de destruição",
		"src/resources/raio.png",
		20.0,
		20.0,
		null);
	
}
