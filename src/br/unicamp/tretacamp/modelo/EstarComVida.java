package br.unicamp.tretacamp.modelo;

public class EstarComVida implements Condicao {
	private double vida;	
	
	public EstarComVida(double vida) {
		this.vida = vida;
	}

	public boolean verificar(Drego drego) {
		return drego.getVida() >= this.vida;
	}

	@Override
	public String toString() {
		return "EstarComVida [vida=" + vida + "]";
	}
	
}
