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
	
	public void diminuirVida(double dano) {
		if (dano > this.vida) {
			this.vida = 0;
		}
		else {
			this.vida -= dano;
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
	
	
}
