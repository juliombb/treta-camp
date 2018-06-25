package br.unicamp.tretacamp.config;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.tretacamp.modelo.efeito.Efeito;
import br.unicamp.tretacamp.modelo.efeito.Enfraquecer;
import br.unicamp.tretacamp.modelo.efeito.Paralisar;
import br.unicamp.tretacamp.modelo.efeito.Queimar;
import br.unicamp.tretacamp.modelo.Poder;

public class ConfiguracaoPoder {
	public final static Poder SOCO = new Poder(
		"Soco",
		"Um simples socão na boca",
		"src/resources/soco.png",
		10.0,
		0.0,
		null);
	
	public final static Poder CHUTE = new Poder(
		"Chute",
		"O Roundhouse Kick do agreste",
		"src/resources/chute.png",
		12.0,
		0.0,
		null);

	public final static Poder MORDIDA = new Poder(
		"Mordida",
		"Uma mordida sórdida. Só os mais arretados são capazes de " +
			"executá-la.",
		"src/resources/mordida.gif",
		15.0,
		0.0,
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

	private final static ArrayList<Efeito> EFEITOS_NECROMANCIA =
		new ArrayList<>(Arrays.asList(
			new Queimar(3.0, 3),
			new Paralisar(2)));

	public final static Poder NECROMANCIA = new Poder(
		"Necromancia",
		"Os deuses da morte servem ao adversário uma dose do seu doce elixir",
		"src/resources/necromancia.png",
		13.0,
		10.0,
		EFEITOS_NECROMANCIA);

	public final static Poder ESPADA = new Poder(
		"Espada mágica",
		"Usa uma espada oculta em seu coração para desferir um golpe redentor",
		"src/resources/espada.png",
		20.0,
		15.0,
		null);

	private final static ArrayList<Efeito> EFEITOS_DONUT =
		new ArrayList<>(Arrays.asList(
			new Paralisar(1)));

	public final static Poder GATO_DONUT = new Poder(
		"Gato donut",
		"Um gato espacial desce e desintegra tudo em um raio de um donut",
		"src/resources/gatodonut.png",
		50.0,
		60.0,
		EFEITOS_DONUT);

	private final static ArrayList<Efeito> EFEITOS_GELO =
		new ArrayList<>(Arrays.asList(
			new Paralisar(5)));

	public final static Poder GELO = new Poder(
		"Gelinho",
		"Congela o adversário e causa um pouco de dano",
		"src/resources/gelo.png",
		2.0,
		20.0,
		EFEITOS_GELO);
	
}
