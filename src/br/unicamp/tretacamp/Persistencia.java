package br.unicamp.tretacamp;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.unicamp.tretacamp.modelo.Drego;

public class Persistencia {
	private ArrayList<Drego> dregos;
	private int fase;
	
	public Persistencia() {
		dregos = new ArrayList<Drego>();
	}

	 public void salvarEstadoJogo(Drego jogador, Drego inimigo, int fase) {
	    	String filename = "jogo.dat";
	
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream (filename));
			output.writeObject(jogador);   		
			output.writeObject(inimigo);
			output.writeInt(fase);
			System.out.print(fase);
			output.flush();
			output.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
    
	public ArrayList<Drego> carregarEstadoJogo() {
		String filename = "jogo.dat";
		
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
			Drego d1 = (Drego) input.readObject();
			Drego d2 = (Drego) input.readObject();
			this.fase = input.readInt();
			input.close();
			
			dregos.add(d1);
			dregos.add(d2);
			return dregos;
		}
		catch (EOFException endOfFileException) {
			endOfFileException.printStackTrace();
		}
		catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public int getFase() {
		return fase;
	}	
}
