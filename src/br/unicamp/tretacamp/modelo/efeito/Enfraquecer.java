package br.unicamp.tretacamp.modelo.efeito;

import br.unicamp.tretacamp.modelo.Drego;

public class Enfraquecer extends Efeito {

	private static final long serialVersionUID = 971243691728442L;

	public Enfraquecer(double valor, int duracaoEmTurnos) {
		super(valor, duracaoEmTurnos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean acontecer(Drego drego) {
		if (this.reduzirDuracaoEmTurnos()) {
			double novaEnergia = drego.getEnergia() - this.getValor();
			drego.setEnergia(novaEnergia > 0 ? novaEnergia : 0);
			return true;
        }
		
		return false;
	}

	@Override
	public Efeito clone() {
		return new Enfraquecer(getValor(), getDuracaoEmTurnos());
	}
	
	
}
