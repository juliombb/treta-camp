package br.unicamp.tretacamp.modelo;

public enum Tipo {
	AGUA("Água"),
	TERRA("Terra"),
	FOGO("Fogo"),
	VENTO("Vento"),
	METAL("Metal"),
	SONORO("Sonoro"),
	TREVAS("Trevas");

	private final String nome;

	Tipo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
}
