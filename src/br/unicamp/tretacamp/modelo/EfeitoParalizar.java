package br.unicamp.tretacamp.modelo;

public class EfeitoParalizar extends Efeito {

	private static final long serialVersionUID = -21232970679943017L;

	public EfeitoParalizar(double valor, int duracaoEmTurnos) {
		super(valor, duracaoEmTurnos);
	}

	@Override
	// not working yet
	public void acontecer(Drego drego) {
		this.reduzirDuracaoEmTurnos();
	}

	@Override
	public Efeito clone() {
		return new EfeitoParalizar(getValor(), getDuracaoEmTurnos());
	}
	

}
