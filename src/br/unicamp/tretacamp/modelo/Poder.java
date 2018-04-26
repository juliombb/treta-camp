package br.unicamp.tretacamp.modelo;

import java.util.ArrayList;

public class Poder {
	private final String nome;
	private final ArrayList<Efeito> efeitos;
	private final double danoInstantaneo;
	private final double custo;
	
	public Poder(String nome, double dano, double custo, ArrayList<Efeito> efeitos) {
		this.nome = nome;
		this.danoInstantaneo = dano;
		this.custo = custo;
		this.efeitos = efeitos;
	}
	
	public boolean aplicar(Drego conjurante, Drego atingido) {
		if (conjurante.getEfeitos()
				.stream()
				.anyMatch((efeito) ->
					efeito.getTipoEfeito() == TipoEfeito.PARALIZAR
					&& efeito.getDuracaoEmTurnos() > 0)) {

			return false;
		}

		if (conjurante.getEnergia() < this.custo) {
			return false;
		}
		
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
		
		this.efeitos.forEach((efeito) -> {
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
				case ENFRAQUECER:
					atingido.adicionarEfeito(efeito.clone());
					break;
				default:
					break;
			}
		});
		
		conjurante.gastarEnergia(this.custo);
		
		return true;
	}
	
	public String getNome() {
		return nome;
	}

	public ArrayList<Efeito> getEfeitos() {
		return efeitos;
	}

	public double getDanoInstantaneo() {
		return danoInstantaneo;
	}

	public double getCusto() {
		return custo;
	}

	@Override
	public String toString() {
		return "Poder [nome=" + nome + ", efeitos=" + efeitos + ", danoInstantaneo=" + danoInstantaneo + ", custo=" + custo + "]";
	}
}
