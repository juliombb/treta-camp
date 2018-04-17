package br.unicamp.tretacamp.modelo;

import java.util.ArrayList;

public class Poder {
	private String nome;
	private ArrayList<Efeito> efeitos;
	private double dano;
	private double custo;
	
	public void aplicar (Drego conjurante, Drego atingido) {
		/*for(int i=0; i<efeitos.size(); i++) {
			switch(efeitos.get(i)) {
				case PARALIZAR:
					
			}
		}*/
		atingido.diminuirVida(this.dano);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Efeito> getEfeitos() {
		return efeitos;
	}

	public void setEfeitos(ArrayList<Efeito> efeitos) {
		this.efeitos = efeitos;
	}

	public double getDano() {
		return dano;
	}

	public void setDano(double dano) {
		this.dano = dano;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}
	
}
