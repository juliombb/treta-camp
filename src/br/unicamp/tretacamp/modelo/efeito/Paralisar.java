package br.unicamp.tretacamp.modelo.efeito;

import br.unicamp.tretacamp.modelo.Drego;

public class Paralisar extends Efeito {

	private static final long serialVersionUID = -21232970679943017L;

	public Paralisar(int duracaoEmTurnos) {
		super(0.0, duracaoEmTurnos);
	}

	@Override
	// not working yet
	public boolean acontecer(Drego drego) {
		return this.reduzirDuracaoEmTurnos();
	}

	@Override
	public Efeito clone() {
		return new Paralisar(getDuracaoEmTurnos());
	}
	

}
