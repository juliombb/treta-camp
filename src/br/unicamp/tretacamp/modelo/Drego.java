package br.unicamp.tretacamp.modelo;
import java.util.ArrayList;

public class Drego {
	private String nome;
	private ArrayList<Poder> poderes;
	private String visual;
	private double vida;
	private double energia;
	private Tipo tipo;
	private Diferencial diferencial;
	private ArrayList<Item> itens;
	private ArrayList<Efeito> efeitos;
	
	public Drego() {
		this.itens = new ArrayList<Item>();
		this.efeitos = new ArrayList<Efeito>();
		this.poderes = new ArrayList<Poder>();
	}
	
	public Drego(String nome, String visual, double vida, double energia, Tipo tipo, Diferencial diferencial) {
		this.nome = nome;
		this.visual = visual;
		this.vida = vida;
		this.energia = energia;
		this.tipo =tipo;
		this.diferencial = diferencial;
		this.itens = new ArrayList<Item>();
		this.efeitos = new ArrayList<Efeito>();
		this.poderes = new ArrayList<Poder>();
	}
	
	public void diminuirVida(double dano) {
		if (dano > this.vida) {
			this.vida = 0;
		}
		else {
			this.vida -= dano;
		}
	}
	
	public void aumentarVida(double aumento) {
		this.vida += aumento; 
	}
	
	public void aumentarEnergia(double aumento) {
		this.energia += aumento;
	}
	
	public boolean gastarEnergia(double gasto) {
		if (gasto > this.energia) {
			return false;
		}
		else {
			this.energia -= gasto;
			return true;
		}
	}
	
	public boolean adicionarEfeito(Efeito efeito) {
		if(!efeitos.contains(efeito)) {
			return efeitos.add(efeito);
		}
		
		return false;
	}
	
	public boolean adicionarItem(Item item) {
		if(!itens.contains(item)) {
			return itens.add(item);
		}
		
		return false;
	}
	
	public boolean adicionarPoder(Poder poder) {
		if (!poderes.contains(poder)) {
			return this.poderes.add(poder);
		}
		
		return false;
	}	
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getVisual() {
		return visual;
	}
	
	public void setVisual(String visual) {
		this.visual = visual;
	}
	
	public double getVida() {
		return vida;
	}
	
	public void setVida(double vida) {
		this.vida = vida;
	}
	
	public double getEnergia() {
		return energia;
	}
	
	public void setEnergia(double energia) {
		this.energia = energia;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public Diferencial getDiferencial() {
		return diferencial;
	}
	
	public void setDiferencial(Diferencial diferencial) {
		this.diferencial = diferencial;
	}
	
	public ArrayList<Item> getItens() {
		return itens;
	}
	
	public ArrayList<Efeito> getEfeitos() {
		return efeitos;
	}
	
	public void setEfeitos(ArrayList<Efeito> efeitos) {
		this.efeitos = efeitos;
	}
	
	public ArrayList<Poder> getPoderes() {
		return poderes;
	}

	@Override
	public String toString() {
		return "Drego [nome=" + nome + ", poderes=" + poderes + ", visual=" + visual + ", vida=" + vida + ", energia="
				+ energia + ", tipo=" + tipo + ", diferencial=" + diferencial + ", itens=" + itens + ", efeitos="
				+ efeitos + "]";
	}
	
	public Drego clone() {
		Drego clone = new Drego(this.nome, this.visual, this.vida, this.energia, this.tipo, this.diferencial);
		
		if (this.poderes.isEmpty()) {
			clone.poderes = new ArrayList<Poder>();
		} else {
			ArrayList<Poder> poderes = new ArrayList<Poder>();
			poderes.addAll(this.poderes);
			clone.poderes = poderes;
		}
		
		if (this.itens.isEmpty()) {
			clone.itens = new ArrayList<Item>();
		} else {
			ArrayList<Item> itens = new ArrayList<Item>();
			itens.addAll(this.itens);
			clone.poderes = poderes;
		}
		
		if (this.efeitos.isEmpty()) {
			clone.efeitos = new ArrayList<Efeito>();
		} else {
			ArrayList<Efeito> efeitos = new ArrayList<Efeito>();
			efeitos.addAll(this.efeitos);
			clone.poderes = poderes;
		}
		
		return clone;
	}

}
