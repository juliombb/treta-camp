package br.unicamp.tretacamp.modelo;

import java.util.ArrayList;
import java.util.Arrays;

public enum Tipo {
	AGUA("Água"),
	TERRA("Terra"),
	FOGO("Fogo"),
	VENTO("Vento"),
	METAL("Metal"),
	SONORO("Sonoro");

	private final String nome;

	Tipo(String nome) {
		this.nome = nome;
	}
}
