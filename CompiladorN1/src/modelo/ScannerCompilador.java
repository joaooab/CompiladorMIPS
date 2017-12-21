package modelo;

public class ScannerCompilador {
	Token proxToken;
	String entrada;
	int posicao = 0;

	public ScannerCompilador(String entrada) {

		this.entrada = entrada;
	}

	public void voltaPosicao() {
		--posicao;
	}

	public Token obterProximoToken() {

		if (posicao >= entrada.length()) {
			return null;
		}

		while (entrada.charAt(posicao) == ' ') {
			posicao++;
		}

		if (Character.isDigit(entrada.charAt(posicao))) {
			String valorStr = "" + entrada.charAt(posicao);
			posicao++;
			while (posicao < entrada.length() && Character.isDigit(entrada.charAt(posicao))) {
				valorStr += entrada.charAt(posicao);
				posicao++;
			}
			return new Token(TipoToken.NUMERO, Integer.parseInt(valorStr));
		} else if (entrada.charAt(posicao) == '+') {
			posicao++;
			return new Token(TipoToken.SOMA, null);
		} else if (entrada.charAt(posicao) == '-') {
			posicao++;
			return new Token(TipoToken.SUB, null);
		} else if (entrada.charAt(posicao) == '*') {
			posicao++;
			return new Token(TipoToken.MUL, null);
		} else if (entrada.charAt(posicao) == '/') {
			posicao++;
			return new Token(TipoToken.DIV, null);
		} else if (entrada.charAt(posicao) == '(') {
			posicao++;
			return new Token(TipoToken.ABRE_PARENTESES, null);
		} else if (entrada.charAt(posicao) == ')') {
			posicao++;
			return new Token(TipoToken.FECHA_PARENTESES, null);
		} else if (entrada.charAt(posicao) == '=') {
			posicao++;
			return new Token(TipoToken.ATRIBUICAO, null);
		}  else if (entrada.toUpperCase().charAt(posicao) == 'V') {
			String valorStr = "" + entrada.charAt(posicao);
			posicao++;
			if (entrada.toUpperCase().charAt(posicao) == 'A') {
				valorStr += entrada.charAt(posicao);
				posicao++;
				if (entrada.toUpperCase().charAt(posicao) == 'R') {
					posicao++;
					return new Token(TipoToken.VAR, null);
				}
			}
			while (posicao < entrada.length() && (Character.isAlphabetic(entrada.charAt(posicao))
					|| Character.isDigit(entrada.charAt(posicao)))) {
				valorStr += entrada.charAt(posicao);
				posicao++;
			}
			return new Token(TipoToken.ID, valorStr);
		} else if (entrada.charAt(posicao) == ';') {
			posicao++;
			return new Token(TipoToken.PONTO_VIRGULA, null);
		} else if (Character.isAlphabetic(entrada.charAt(posicao))) {
			String valorStr = "" + entrada.charAt(posicao);
			posicao++;
			while (posicao < entrada.length() && (Character.isAlphabetic(entrada.charAt(posicao))
					|| Character.isDigit(entrada.charAt(posicao)))) {
				valorStr += entrada.charAt(posicao);
				posicao++;
			}
			return new Token(TipoToken.ID, (String) valorStr);
		} else {
			posicao++;
			return new Token(TipoToken.ERRO, null);
		}
	}
}
