package br.unicamp.tretacamp.modelo;

public class NotEnoughEnergyException extends Exception {

	private static final long serialVersionUID = 24513451L;
	
	public NotEnoughEnergyException() {
		super();
	}
	
	public NotEnoughEnergyException(String message) {
		super(message);
	}

}
