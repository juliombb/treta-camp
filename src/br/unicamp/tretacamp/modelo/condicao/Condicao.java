package br.unicamp.tretacamp.modelo.condicao;

import br.unicamp.tretacamp.modelo.Drego;

import java.io.Serializable;

public interface Condicao extends Serializable {
	
	public boolean verificar(Drego drego);

	public default String getNome() {
		return this.getClass().getSimpleName();
	}
}
