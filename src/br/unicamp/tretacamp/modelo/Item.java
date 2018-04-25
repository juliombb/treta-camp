package br.unicamp.tretacamp.modelo;

public class Item {
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
	
}
