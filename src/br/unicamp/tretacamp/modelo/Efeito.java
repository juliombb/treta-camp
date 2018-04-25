package br.unicamp.tretacamp.modelo;

public class Efeito {
	private TipoEfeito tipoEfeito;
	private double valor;
	private int duracaoEmTurnos;
	
	public Efeito(TipoEfeito tipoEfeito, double valor, int duracaoEmTurnos) {
		this.tipoEfeito = tipoEfeito;
		this.valor = valor;
		this.duracaoEmTurnos = duracaoEmTurnos;
	}

	public TipoEfeito getTipoEfeito() {
		return tipoEfeito;
	}

	public void setTipoEfeito(TipoEfeito tipoEfeito) {
		this.tipoEfeito = tipoEfeito;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getDuracaoEmTurnos() {
		return duracaoEmTurnos;
	}

	public void setDuracaoEmTurnos(int duracaoEmTurnos) {
		this.duracaoEmTurnos = duracaoEmTurnos;
	}
	
}
