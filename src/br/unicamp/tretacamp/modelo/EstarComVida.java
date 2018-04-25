package br.unicamp.tretacamp.modelo;

public class EstarComVida implements Condicao {
	private double vida;
	
	public boolean verificar(Drego drego) {
		return drego.getVida() >= this.vida;
	}
}
