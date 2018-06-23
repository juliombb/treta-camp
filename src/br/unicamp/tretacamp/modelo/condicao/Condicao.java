package br.unicamp.tretacamp.modelo.condicao;

import br.unicamp.tretacamp.modelo.Drego;

public interface Condicao {
	
	public boolean verificar(Drego drego);

	public default String getNome() {
		return this.getClass().getSimpleName();
	}
}
