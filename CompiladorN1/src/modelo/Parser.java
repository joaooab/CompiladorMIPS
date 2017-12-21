package modelo;

public class Parser {
	String expressaoTxt;
	ScannerCompilador scn;
	boolean erro = false;
	int quantidadeDeParenteses;

	public Parser(String expressaoTxt) {
		this.expressaoTxt = expressaoTxt;
		scn = new ScannerCompilador(expressaoTxt);
	}

	public boolean isExpressaoValida() {
		Expressao();
		if (quantidadeDeParenteses != 0) {
			System.out.println("ERRO: parenteses");
			erro = true;
		}
		return erro ? false : true ;
	}

	void Expressao() {
		if (termo()) {
			Restante1();
		} else {
			erro = true;
			System.out.println("ERRO: Expressao nao inicia com NUMERO");
		}
	}

	void Restante1() {
		Token token = scn.obterProximoToken();

		if (token != null && token.tipo == TipoToken.SOMA) {
			if (termo()) {
				Restante1();
			}
		} else if (token != null && token.tipo == TipoToken.SUB) {
			if (termo()) {
				Restante1();
			}
		} else if (token != null && token.tipo == TipoToken.ABRE_PARENTESES) {
			if (termo()) {
				Restante1();
			}
		} 	else {
			if (token != null) {
				scn.voltaPosicao();
			}
		}
	}

	void Restante2() {
		Token token = scn.obterProximoToken();

		if (token != null && token.tipo == TipoToken.MUL) {
			if (fator()) {
				Restante2();
				Restante1();
			}
		} else if (token != null && token.tipo == TipoToken.DIV) {
			if (fator()) {
				Restante2();
				Restante1();
			}
		} else if (token != null && token.tipo == TipoToken.ABRE_PARENTESES) {
			erro = true;
			System.out.println("ERRO: Faltou operador");
		} else if (token != null && token.tipo == TipoToken.FECHA_PARENTESES) {
			quantidadeDeParenteses--;
			scn.voltaPosicao();
		} else {
			if (token != null) {
				scn.voltaPosicao();
			}
		}
	}

	private boolean termo() {
		if (fator()) {
			Restante2();
			return true;
		} else {
			return false;
		}

	}

	private boolean fator() {
		Token token = scn.obterProximoToken();
		if (token != null && token.tipo == TipoToken.ABRE_PARENTESES) {
			quantidadeDeParenteses++;
			Expressao();
			token = scn.obterProximoToken();
			if (token != null && token.tipo == TipoToken.FECHA_PARENTESES) {
				return true;
			} else {
				erro = true;
				System.out.println("ERRO: Faltou fechar parenteses");
				return false;
			}
		} else if (token != null && token.tipo == TipoToken.FECHA_PARENTESES) {
			erro =  true;
			System.out.println("ERRO: nao abriu parenteses");
			return false;
		} else if (token == null || token.tipo != TipoToken.NUMERO) {
			erro = true;
			System.out.println("ERRO: Numero esperado");
			return false;
		}
		return true;
	}
}
