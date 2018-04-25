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
		
		if (atingido.getDiferencial() != null) {
			// Caso o Drego atingido tenha o diferencial de DEFESA_PERFURANTE é
			// causado um dano de 20% do dano causado no drego atingido no drego conjurante
			if (atingido.getDiferencial().equals(Diferencial.DEFESA_PERFURANTE)) {
				conjurante.diminuirVida(0.2 * this.danoInstantaneo);
			}
		}
		
		/*
		 * Os efeitos que um poder causa podem ter impactos tanto no momento
		 * quanto a longo prazo
		 * 
		 * Caso o efeito seja instantâneo ele é simplesmente aplicado ao Drego atingido
		 * já se o efeito for a longo prazo, como por exemplo QUEIMAR que pode causar
		 * danos durante alguns próximos turnos, esse efeito é propagado para o Drego,
		 * mas é criado um clone do objeto de efeito, pois ele poderá ser modificado na
		 * classe Drego, e não queremos que essas modificações alterem o estado do efeito
		 * que está nessa classe Poder.
		 * 
		 * */
		
		efeitos.forEach((efeito) -> {
			switch(efeito.getTipoEfeito()) {
				case PARALIZAR:
					atingido.adicionarEfeito(efeito.clone());
					break;
				case QUEIMAR:
					if (atingido.getDiferencial() != null) {
						if (!atingido.getDiferencial().equals(Diferencial.PROTECAO_FOGO)) {
							atingido.adicionarEfeito(efeito.clone());
						}
					}
					break;
				case SUGAR:
					conjurante.aumentarVida(0.2 * this.danoInstantaneo);
					break;
				case ATORDOAR:
					atingido.adicionarEfeito(efeito.clone());
					break;
				case ENFRAQUECER:
					atingido.adicionarEfeito(efeito.clone());
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
		if (!efeitos.contains(efeito)) {
			return this.efeitos.add(efeito);
		}
		
		return false;
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
