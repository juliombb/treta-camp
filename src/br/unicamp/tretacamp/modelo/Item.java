package br.unicamp.tretacamp.modelo;

import java.io.Serializable;

public class Item implements Serializable{
	private static final long serialVersionUID = 505L;
	private final String nome;
	private final String imagem;

	public Item(String nome, String imagem) {
		this.nome = nome;
		this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}
	
	public String getImagem() {
		return imagem;
	}

	@Override
	public String toString() {
		return "Item [nome=" + nome + ", imagem=" + imagem + "]";
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			return ((Item) obj).getNome() == this.getNome() && ((Item) obj).getImagem() == this.getImagem();
		}
		
		return false;
	}
	
}
