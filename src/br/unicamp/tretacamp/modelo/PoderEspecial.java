package br.unicamp.tretacamp.modelo;

public class PoderEspecial extends Poder {
	private Condicao condicao;
	
	public PoderEspecial(String nome, double dano, double custo, Condicao condicao) {
		super(nome, dano, custo);
		this.condicao = condicao;
	}
	
	@Override
	public void aplicar (Drego conjurante, Drego atingido) {
		if (condicao.verificar(conjurante)) {
			super.aplicar(conjurante, atingido);
			
			// extras
		}
	}

	public Condicao getCondicao() {
		return condicao;
	}

	public void setCondicao(Condicao condicao) {
		this.condicao = condicao;
	}
	
}
