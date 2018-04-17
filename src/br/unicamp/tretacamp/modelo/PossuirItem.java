package br.unicamp.tretacamp.modelo;

public class PossuirItem implements Condicao {
	private Item item;
	
	PossuirItem(Item item) {
		this.item = item;
	}
	
	public boolean verificar(Drego drego) {
		for(int i=0; i<drego.getItens().size(); i++) {
			if(drego.getItens().get(i)==this.item) {
				return true;
			}
		}
		return false;
	}
}
