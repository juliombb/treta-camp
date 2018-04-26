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
	
	public boolean reduzirDuracaoEmTurnos() {
		if (this.duracaoEmTurnos > 0) {
			this.duracaoEmTurnos--;
			return true;
		}
		
		return false;
	}
	
	public Efeito clone() {
		return new Efeito(this.tipoEfeito, this.valor, this.duracaoEmTurnos);
	}

	@Override
	public String toString() {
		return "Efeito [tipoEfeito=" + tipoEfeito + ", valor=" + valor + ", duracaoEmTurnos=" + duracaoEmTurnos + "]";
	}
	
}
