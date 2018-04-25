package br.unicamp.tretacamp.modelo;

public enum Diferencial {
	PROTECAO_FOGO,
	DEFESA_PERFURANTE;
	
	private String nome;
	private double valor;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
}
