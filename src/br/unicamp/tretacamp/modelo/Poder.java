package br.unicamp.tretacamp.modelo;

import java.util.ArrayList;

public class Poder {
	private String nome;
	private ArrayList<Efeito> efeitos;
	private double danoInstantaneo;
	private double custo;
	
	public Poder(String nome, double dano, double custo) {
		this.nome = nome;
		this.danoInstantaneo = dano;
		this.custo = custo;
		this.efeitos = new ArrayList<Efeito>();
	}
	
	public void aplicar(Drego conjurante, Drego atingido) {
		atingido.diminuirVida(this.danoInstantaneo);
		
		if (atingido.getDiferencial().equals(Diferencial.DEFESA_PERFURANTE)) {
			conjurante.diminuirVida(0.2 * this.danoInstantaneo);
		}
		
		efeitos.forEach((efeito) -> {
			switch(efeito.getTipoEfeito()) {
			case PARALIZAR:
				atingido.adicionarEfeito(efeito);
				break;
			case QUEIMAR:
				if (!atingido.getDiferencial().equals(Diferencial.PROTECAO_FOGO)) {
					atingido.adicionarEfeito(efeito);
				}
				break;
			case SUGAR:
				conjurante.aumentarVida(0.2 * this.danoInstantaneo);
				break;
			case ATORDOAR:
				atingido.adicionarEfeito(efeito);
				break;
			case ENFRAQUECER:
				atingido.adicionarEfeito(efeito);
				break;
			default:
				break;
		}
		
		});
		
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Efeito> getEfeitos() {
		return efeitos;
	}

	public boolean adicionarEfeito(Efeito efeito) {
		return this.efeitos.add(efeito);
	}

	public double getDanoInstantaneo() {
		return danoInstantaneo;
	}

	public void setDanoInstantaneo(double dano) {
		this.danoInstantaneo = dano;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	@Override
	public String toString() {
		return "Poder [nome=" + nome + ", efeitos=" + efeitos + ", danoInstantaneo=" + danoInstantaneo + ", custo=" + custo + "]";
	}
}
