package br.unicamp.tretacamp.modelo;

public class EfeitoEnfraquecer extends Efeito {

	private static final long serialVersionUID = 971243691728442L;

	public EfeitoEnfraquecer(double valor, int duracaoEmTurnos) {
		super(valor, duracaoEmTurnos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void acontecer(Drego drego) {
		if (this.reduzirDuracaoEmTurnos()) {
			double novaEnergia = drego.getEnergia() - this.getValor();
			drego.setEnergia(novaEnergia > 0 ? novaEnergia : 0);
        }
	}

	@Override
	public Efeito clone() {
		return new EfeitoEnfraquecer(getValor(), getDuracaoEmTurnos());
	}
	
	
}
