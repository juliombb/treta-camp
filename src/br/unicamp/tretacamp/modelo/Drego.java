package br.unicamp.tretacamp.modelo;
import java.util.ArrayList;

public class Drego {
	private String nome;
	private Poder[] poder;
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
	
	public void adicionarEfeito(Efeito efeito) {
		if(!efeitos.contains(efeito)) {
			efeitos.add(efeito);
		}
	}
	
	public void adicionarItem(Item item) {
		if(!itens.contains(item)) {
			itens.add(item);
		}
	}
		
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Poder[] getPoder() {
		return poder;
	}
	public void setPoder(Poder[] poder) {
		this.poder = poder;
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
	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}
	public ArrayList<Efeito> getEfeitos() {
		return efeitos;
	}
	public void setEfeitos(ArrayList<Efeito> efeitos) {
		this.efeitos = efeitos;
	}
}
