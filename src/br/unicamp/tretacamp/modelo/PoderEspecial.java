package br.unicamp.tretacamp.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoderEspecial extends Poder {
	private Condicao condicao;
	private final static Map<Tipo, List<Tipo>> tipoFraquezas = popularTipoFraquezas();
	
	public PoderEspecial(String nome, double dano, double custo, Condicao condicao, ArrayList<Efeito> efeitos) {
		super(nome, dano, custo, efeitos);
		this.condicao = condicao;
	}
	
	
	/*
	 * Poder especial, além de aplicar normalmente o poder caso seja válida uma condição,
	 * também aumenta o dano causado caso o tipo do Drego seja atingido seja fraco contra
	 * o tipo do Drego conjurante.
	 * */
	@Override
	public boolean aplicar (Drego conjurante, Drego atingido) {
		if (condicao.verificar(conjurante)) {
			if (super.aplicar(conjurante, atingido)) {
				double porcentagemAumento = getPorcentagemAumentoEfeito(conjurante, atingido);
				
				atingido.diminuirVida(porcentagemAumento * getDanoInstantaneo());
				
				return true;
			}
		}
		
		return false;
	}

	public Condicao getCondicao() {
		return condicao;
	}

	public void setCondicao(Condicao condicao) {
		this.condicao = condicao;
	}
	
	public double getPorcentagemAumentoEfeito(Drego conjurante, Drego atingido) {
		List<Tipo> fraquezasTipoAtingido = tipoFraquezas.get(atingido.getTipo());
		
		if (fraquezasTipoAtingido.contains(conjurante.getTipo())) {
			return 0.25;
		}
		
		return 0.0;
	}
	
	private static Map<Tipo, List<Tipo>> popularTipoFraquezas() {
		Map<Tipo, List<Tipo>> tipoFraquezas = new HashMap<Tipo, List<Tipo>>();
		
		tipoFraquezas.put(Tipo.AGUA, Arrays.asList(Tipo.TERRA, Tipo.METAL));
		tipoFraquezas.put(Tipo.FOGO, Arrays.asList(Tipo.AGUA, Tipo.VENTO));
		tipoFraquezas.put(Tipo.METAL, Arrays.asList(Tipo.FOGO, Tipo.SONORO));
		tipoFraquezas.put(Tipo.SONORO, Arrays.asList(Tipo.TERRA, Tipo.AGUA));
		tipoFraquezas.put(Tipo.TERRA, Arrays.asList(Tipo.FOGO, Tipo.VENTO));
		tipoFraquezas.put(Tipo.VENTO, Arrays.asList(Tipo.SONORO, Tipo.METAL));
		
		return tipoFraquezas;
	}

	@Override
	public String toString() {
		return "PoderEspecial [" + super.toString() + ", condicao=" + condicao + "]";
	}
	
	
}
