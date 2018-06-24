package br.unicamp.tretacamp.modelo.efeito;

import br.unicamp.tretacamp.modelo.Drego;

public class Paralisar extends Efeito {

	private static final long serialVersionUID = -21232970679943017L;

	public Paralisar(double valor, int duracaoEmTurnos) {
		super(valor, duracaoEmTurnos);
	}

	@Override
	// not working yet
	public String acontecer(Drego drego) {
		if (this.reduzirDuracaoEmTurnos()) {
			return drego.getNome() + " ainda está paralizado e não cosegue aplicar seu poder";
		}
		
		return null;
	}

	@Override
	public Efeito clone() {
		return new Paralisar(getValor(), getDuracaoEmTurnos());
	}
	

}
