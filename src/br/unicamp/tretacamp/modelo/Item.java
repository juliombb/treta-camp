package br.unicamp.tretacamp.modelo;

import java.io.Serializable;

public class Item implements Serializable{
	private static final long serialVersionUID = 505L;
	private String nome;
	private String imagem;

	public Item(String nome, String imagem) {
		this.nome = nome;
		this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setImagem(String imagem) {
		this.imagem = imagem;
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
