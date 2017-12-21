package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import modelo.Parser;

public class ExemploParserTest {

	@Test //verifica se as expressoes sao validas
	public void validacaoParserTrue() {
		Parser p = new Parser("2+3");
        assertTrue(p.isExpressaoValida());
        Parser p2 = new Parser("2+(3+2)");
        assertTrue(p2.isExpressaoValida());
        Parser p3 = new Parser("(5*3-4/(8+6)*9)+8*(4+7)");
        assertTrue(p3.isExpressaoValida());
	}
	
	@Test //verifica se as expressoes sao invalidas
	public void validacaoParserFalse() {
		Parser p = new Parser("2+3+");
        assertTrue(!p.isExpressaoValida());
        Parser p2 = new Parser("2+(3 2)");
        assertTrue(!p2.isExpressaoValida());
        Parser p3 = new Parser("2+(3+2");
        assertTrue(!p3.isExpressaoValida());
        Parser p4 = new Parser("2+3+2)");
        assertTrue(!p4.isExpressaoValida());
	}

}
