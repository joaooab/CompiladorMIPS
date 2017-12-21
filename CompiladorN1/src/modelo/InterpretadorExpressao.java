package modelo;

import java.util.Stack;

public class InterpretadorExpressao {
	String expressaoTxt;
	ScannerCompilador scn;
	Stack<Float> resultado = new Stack<>();

	public InterpretadorExpressao(String ex) {
		expressaoTxt = ex;
		scn = new ScannerCompilador(expressaoTxt);
	}
	
	public float getResultado(){
		Expressao();
		return resultado.pop();
	}

	void Expressao() {
		if (termo()) {
			Restante1();
		} else {
			System.out.println("ERRO: Expressão não inicia com NUMERO");
		}
	}

	public void imprimirResultado() {
		for (Float a : resultado) {
			System.out.println(a);
		}
	}

	void Restante1() {
		Token token = scn.obterProximoToken();

		if (token != null && token.getTipo() == TipoToken.SOMA) {
			if (termo()) {
				float b = resultado.pop();
				float a = resultado.pop();
				resultado.push(a + b);
				Restante1();
			}
		} else if (token != null && token.getTipo() == TipoToken.SUB) {
			if (termo()) {
				float b = resultado.pop();
				float a = resultado.pop();
				resultado.push(a - b);
				Restante1();
			}
			Restante1();
		} else if (token != null && token.getTipo() == TipoToken.ABRE_PARENTESES) {
			if (termo()) {
				Restante1();
			}
		} else {
			if (token != null) {
				scn.voltaPosicao();
			}
		}
	}

	void Restante2() {
		Token token = scn.obterProximoToken();

		if (token != null && token.getTipo() == TipoToken.MUL) {
			if (fator()) {
				float b = resultado.pop();
				float a = resultado.pop();
				resultado.push(a * b);
				Restante2();
				Restante1();
			}
		} else if (token != null && token.getTipo() == TipoToken.DIV) {
			if (fator()) {
				float b = resultado.pop();
				float a = resultado.pop();
				resultado.push(a / b);
				Restante2();
				Restante1();
			}
		} else if (token != null && token.getTipo() == TipoToken.FECHA_PARENTESES) {
			scn.voltaPosicao();
		} else {
			if (token != null) {
				scn.voltaPosicao();
			}
		}
	}

	private Boolean termo() {
		if (fator()) {
			Restante2();
			return true;
		} else {
			return false;
		}
	}

	private boolean fator() {
		Token token = scn.obterProximoToken();
		if (token != null && token.getTipo() == TipoToken.ABRE_PARENTESES) {
			Expressao();
			token = scn.obterProximoToken();
			if (token != null && token.getTipo() == TipoToken.FECHA_PARENTESES) {
				return true;
			} else {
				return false;
			}
		} else if (token != null && token.getTipo() == TipoToken.FECHA_PARENTESES) {
			return false;
		} else if (token == null || token.getTipo() != TipoToken.NUMERO) {
			return false;
		}
		resultado.push(Float.valueOf(token.valor.toString()));
		return true;
	}
}