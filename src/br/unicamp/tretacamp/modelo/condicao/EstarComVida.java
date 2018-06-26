package br.unicamp.tretacamp.modelo.condicao;

import br.unicamp.tretacamp.modelo.Drego;

public class EstarComVida implements Condicao {
	private double vida;	
	
	public EstarComVida(double vida) {
		this.vida = vida;
	}

	public boolean verificar(Drego drego) {
		return drego.getVida() <= this.vida;
	}

	@Override
	public String getNome() { return "Estar com " + vida + " de vida"; }

	@Override
	public String toString() {
		return "EstarComVida [vida=" + vida + "]";
	}
	
}
