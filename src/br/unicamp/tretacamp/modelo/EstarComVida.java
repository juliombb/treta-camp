package br.unicamp.tretacamp.modelo;

public class EstarComVida implements Condicao {
	private double vida;
	
	public boolean verificar(Drego drego) {
		if(drego.getVida()>=this.vida) {
			return true;
		}
		else {
			return false;
		}
	}
}
