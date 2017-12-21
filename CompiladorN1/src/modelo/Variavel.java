package modelo;

public class Variavel {

	String ID;
	Float valor;
	
	public Variavel(){
	}

	public Variavel(String nome, Float valor) {
		this.ID = nome;
		this.valor = valor;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}	
}
