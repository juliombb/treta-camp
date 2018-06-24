package br.unicamp.tretacamp.modelo.efeito;

import br.unicamp.tretacamp.modelo.Drego;

public class Queimar extends Efeito {

	private static final long serialVersionUID = -3041076874362205009L;
	
	public Queimar(double valor, int duracaoEmTurnos) {
		super(valor, duracaoEmTurnos);
	}

	@Override
	public String acontecer(Drego drego) {
		if (this.reduzirDuracaoEmTurnos()) {
	    		drego.diminuirVida(this.getValor());
	    		
	    		return drego.getNome() + " ainda está queimando e perde " + getValor() + " de vida";
	    }
		
		return null;
	}

	@Override
	public Efeito clone() {
		return new Queimar(getValor(), getDuracaoEmTurnos());
	}

}
