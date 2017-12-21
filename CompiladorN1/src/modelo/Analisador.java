package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

//Alunos: Ana Paula e João Vitor

public class Analisador {

	String expressaoTxt;
	ScannerCompilador scn;
	Hashtable<String, Float> table = new Hashtable<>();

	public Analisador() {
	}

	public Analisador(String expressaoTxt) {
		this.expressaoTxt = expressaoTxt;
		scn = new ScannerCompilador(expressaoTxt);
	}

	public String getExpressaoTxt() {
		return expressaoTxt;
	}

	public void setExpressaoTxt(String expressaoTxt) {
		this.expressaoTxt = expressaoTxt;
	}

	public boolean verifica(String linhaDeCodigo) {
		this.expressaoTxt = linhaDeCodigo;
		scn = new ScannerCompilador(expressaoTxt);
		boolean linha;
		Token token = scn.obterProximoToken();
		while (token != null) {
			if (token != null && token.getTipo() == TipoToken.VAR) {
				linha = declaraVariavel();
			} else if (token != null && token.getTipo() == TipoToken.ID) {
				linha = comando();
			} else {
				return false;
			}
			if (!linha) {
				return false;
			}
			token = scn.obterProximoToken();
		}
		return true;
	}

	public boolean declaraVariavel() {
		Token token = scn.obterProximoToken();
		if (token != null && token.getTipo() == TipoToken.ID) {
			inicializaNaTable((String) token.valor);
			token = scn.obterProximoToken();
			if (token != null && token.getTipo() == TipoToken.PONTO_VIRGULA) {
				return true;
			}
		}
		return false;
	}

	public boolean comando() {
		Token token = scn.obterProximoToken();
		if (token != null && token.getTipo() == TipoToken.ID) {
			String id = (String) token.valor;
			if (!verificaID(token.valor))
				return false;
			token = scn.obterProximoToken();
			String expressao = "";
			if (token != null && token.getTipo() == TipoToken.ATRIBUICAO) {
				token = scn.obterProximoToken();
				if (token != null && token.getTipo() == TipoToken.PONTO_VIRGULA)
					return false;
				while (token != null && token.getTipo() != TipoToken.PONTO_VIRGULA) {
					if (token != null && token.getTipo() == TipoToken.NUMERO) {
						expressao += token.valor.toString();
					} else if(token != null && token.getTipo() == TipoToken.ID){
						int valorAux = Math.round(table.get(token.valor));
						expressao += valorAux;
					} else {
						expressao += expressaoTxt.charAt(scn.posicao - 1);
					}
					token = scn.obterProximoToken();
				}
				Parser parser = new Parser(expressao);
				if (!parser.isExpressaoValida())
					return false;
				InterpretadorExpressao interpretador = new InterpretadorExpressao(expressao);
				float resultado = interpretador.getResultado();
				addTable(id, resultado);
				scn.voltaPosicao();
				token = scn.obterProximoToken();
				if (token != null && token.getTipo() == TipoToken.PONTO_VIRGULA)
					return true;
			} else {
				System.out.println("ERRO: Atribuicao esperada");
				return false;
			}
		} else {
			System.out.println("ERRO: Identificado esperado");
			return false;
		}
		return true;
	}

	public boolean verificaID(Object valor) {
		return table.containsKey(valor);
	}

	public void inicializaNaTable(Object key) {
		table.put((String) key, (float) 0);
	}

	private void addTable(Object key, float valor) {
		table.put((String) key, valor);
	}

}
