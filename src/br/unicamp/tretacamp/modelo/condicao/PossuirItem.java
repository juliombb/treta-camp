package br.unicamp.tretacamp.modelo.condicao;

import br.unicamp.tretacamp.modelo.Drego;
import br.unicamp.tretacamp.modelo.Item;

public class PossuirItem implements Condicao {
	private Item item;
	
	PossuirItem(Item item) {
		this.item = item;
	}
	
	public boolean verificar(Drego drego) {
		for(int i=0; i<drego.getItens().size(); i++) {
			if(drego.getItens().get(i).equals(this.item)) {
				return true;
			}
		}
		return false;
	}
}
