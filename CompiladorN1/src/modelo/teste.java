package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class teste {

	public static void main(String[] args) {
		try {
			Scanner sc =  new Scanner(new FileReader("comandos.txt"));
			Analisador analisador = new Analisador();
			while(sc.hasNext()){
				String linhaDeCodigo = sc.nextLine();
				if(!analisador.verifica(linhaDeCodigo)){
					System.out.println("Codigo nao compilado");
					return;
				}
			}
			System.out.println("Codigo compilado com sucesso");
			imprimiTabelaDeVariaveis(analisador);
		} catch (FileNotFoundException e) {
			System.out.println("Nao conseguiu abrir arquivo: ");
			e.printStackTrace();
		}
		
	}
	static void imprimiTabelaDeVariaveis(Analisador analisador){
		for (String key : analisador.table.keySet()) {
		    System.out.println(key + " = " + analisador.table.get(key));
		}
	}
}
