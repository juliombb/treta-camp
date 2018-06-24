package br.unicamp.tretacamp.modelo.efeito;

import br.unicamp.tretacamp.modelo.Drego;

import java.io.Serializable;

public abstract class Efeito implements Serializable {
	private static final long serialVersionUID = 134124765347272L;
	private double valor;
	private int duracaoEmTurnos;
	
	public abstract boolean acontecer(Drego drego);
	public abstract Efeito clone();
	
	public Efeito(double valor, int duracaoEmTurnos) {
		this.valor = valor;
		this.duracaoEmTurnos = duracaoEmTurnos;
	}

	public String getNome() { return this.getClass().getSimpleName(); }
	
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
	
	@Override
	public String toString() {
		return "Efeito [valor=" + valor + ", duracaoEmTurnos=" + duracaoEmTurnos + "]";
	}
	
}
