package br.unicamp.tretacamp.modelo;

import br.unicamp.tretacamp.modelo.efeito.Efeito;
import br.unicamp.tretacamp.modelo.efeito.Enfraquecer;
import br.unicamp.tretacamp.modelo.efeito.Paralisar;
import br.unicamp.tretacamp.modelo.efeito.Queimar;

import java.io.Serializable;
import java.util.ArrayList;

public class Poder implements Serializable{
	
	private static final long serialVersionUID = -5753349859451331755L;
	private final String nome;
	private final String descricao;
	private final String icone;
	private final ArrayList<Efeito> efeitos;
	private final double danoInstantaneo;
	private final double custo;
	
	public Poder(String nome, String descricao, String icone, double dano, double custo, ArrayList<Efeito> efeitos) {
		this.nome = nome;
		this.descricao = descricao;
		this.icone = icone;
		this.danoInstantaneo = dano;
		this.custo = custo;
		
		if (efeitos != null) {
			this.efeitos = efeitos;
		}
		else {
			this.efeitos = new ArrayList<Efeito>();
		}
	}
	
	public ResultadoPoder aplicar(Drego conjurante, Drego atingido) {
		if (conjurante.getEfeitos()
				.stream()
				.anyMatch((efeito) ->
					efeito instanceof Paralisar
					&& efeito.acontecer(conjurante))) {
			
			return new ResultadoPoder(conjurante.getNome() +
				" estava paralisad@ e não pôde atacar", true);
		}

		try {
			conjurante.gastarEnergia(this.custo);
		} catch (NotEnoughEnergyException e) {
			return new ResultadoPoder(conjurante.getNome() + " não possui energia suficiente" +
				" para conjurar " + this.getNome(), false);
		}

		atingido.diminuirVida(this.danoInstantaneo);
		ResultadoPoder res = new ResultadoPoder(this.getNome() + "!!!"
			+ System.lineSeparator() + conjurante.getNome() +
			" causa " + this.danoInstantaneo + " de dano em " + atingido.getNome(), true);

		if (atingido.getDiferencial() != null) {
			// Caso o Drego atingido tenha o diferencial de DEFESA_PERFURANTE é
			// causado um dano de 20% do dano causado no drego atingido no drego conjurante
			if (atingido.getDiferencial().equals(Diferencial.DEFESA_PERFURANTE)) {
				conjurante.diminuirVida(0.2 * this.danoInstantaneo);
				res.descResultado += System.lineSeparator();
				res.descResultado += "Por conta da defesa perfurante de " + atingido.getNome() +
					", " + conjurante.getNome() + " levou " + (0.2 * this.danoInstantaneo)
					+ " de dano";
			}

			if (atingido.getDiferencial().equals(Diferencial.CACADOR_DE_MANA)) {
			    atingido.aumentarEnergia(0.1 * this.danoInstantaneo);
				res.descResultado += System.lineSeparator();
				res.descResultado += "Por conta de " + atingido.getNome() +
					" ser caçador de mana, " + conjurante.getNome() + " levou " + (0.1 * this.danoInstantaneo)
					+ " de sugação em sua mana";
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
			if (atingido.getEfeitos().stream().anyMatch((ef) -> ef.getNome().equals(efeito.getNome()))) {
				res.descResultado += System.lineSeparator();
				res.descResultado += "O drego " + atingido.getNome() + " ja tem um efeito de " + efeito.getNome();
				return;
			}

			aplicarEfeito(atingido, res, efeito);
		});
		
		
		return res;
	}

	private void aplicarEfeito(Drego atingido, ResultadoPoder res, Efeito efeito) {
		if (efeito instanceof Queimar) {
            if (atingido.getDiferencial() != null) {
                if (!atingido.getDiferencial().equals(Diferencial.PROTECAO_FOGO)) {
                    atingido.adicionarEfeito(efeito.clone());
                    res.descResultado += System.lineSeparator();
                    res.descResultado += "Por conta do poder " + this.getNome() + ", " + atingido.getNome() +
                            " está queimando por " + efeito.getDuracaoEmTurnos() + " turnos!";
                }

                res.descResultado += System.lineSeparator();
                res.descResultado += atingido.getNome() + " se livrou de queimar pois tem proteção " +
                            "a este tipo de efeito!";
            }
        } else if (efeito instanceof Enfraquecer) {
            atingido.adicionarEfeito(efeito.clone());

            res.descResultado += System.lineSeparator();
            res.descResultado += "Por conta do poder " + this.getNome() + ", " + atingido.getNome() +
                    " perderá energia por " + efeito.getDuracaoEmTurnos() + " turnos!";
        }
        else if (efeito instanceof Paralisar){
            atingido.adicionarEfeito(efeito.clone());

            res.descResultado += System.lineSeparator();
            res.descResultado += "Por conta do poder " + this.getNome() + ", " + atingido.getNome() +
                    " ficará paralizado por " + efeito.getDuracaoEmTurnos() + " turnos!";
        }
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Efeito> getEfeitos() {
		return efeitos;
	}

	public String listaEfeitos() {
		return efeitos.stream()
			.map((eft) -> eft.getNome())
			.reduce((a, b) -> a + ", " + b)
			.orElse("Nenhum");
	}

	public double getDanoInstantaneo() {
		return danoInstantaneo;
	}

	public double getCusto() {
		return custo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getIcone() {
		return icone;
	}

	@Override
	public String toString() {
		return "Poder [nome=" + nome + ", efeitos=" + efeitos + ", danoInstantaneo=" + danoInstantaneo + ", custo=" + custo + "]";
	}

	public static class ResultadoPoder {
		String descResultado;
		final boolean aplicado;

		public ResultadoPoder(String descResultado, boolean aplicado) {
			this.descResultado = descResultado;
			this.aplicado = aplicado;
		}

		public String getDesc() {
			return descResultado;
		}

		public void setDesc(String descResultado) {
			this.descResultado = descResultado;
		}

		public boolean foiAplicado() {
			return aplicado;
		}
	}
}
