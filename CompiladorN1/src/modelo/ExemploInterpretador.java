package modelo;

public class ExemploInterpretador {
	public static void main(String[] args) {
		// (5*3-4/(8+6)*9)+8*(4+7)
		String expressao = "2/(3-1)";
		Parser parser = new Parser(expressao);
		if (parser.isExpressaoValida()) {
			InterpretadorExpressao interpretador = new InterpretadorExpressao(expressao);
			float resultado =interpretador.getResultado();
			System.out.println(resultado);
		}
	}
}
