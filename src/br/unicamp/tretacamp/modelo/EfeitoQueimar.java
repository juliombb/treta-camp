package br.unicamp.tretacamp.modelo;

public class EfeitoQueimar extends Efeito {

	private static final long serialVersionUID = -3041076874362205009L;
	
	public EfeitoQueimar(double valor, int duracaoEmTurnos) {
		super(valor, duracaoEmTurnos);
	}

	@Override
	public void acontecer(Drego drego) {
		if (this.reduzirDuracaoEmTurnos()) {
	    		drego.diminuirVida(this.getValor());
	    }
	}

	@Override
	public Efeito clone() {
		return new EfeitoQueimar(getValor(), getDuracaoEmTurnos());
	}

}
