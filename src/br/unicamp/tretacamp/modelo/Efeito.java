package br.unicamp.tretacamp.modelo;

import java.time.Duration;

public enum Efeito {
	PARALIZAR,
	QUEIMAR,
	SUGAR;
	
	private String nome;
	private double valor;
	private Duration duracao;
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
	public Duration getDuracao() {
		return duracao;
	}
	public void setDuracao(Duration duracao) {
		this.duracao = duracao;
	}
	
	
	
}
