package modelo;

import org.junit.Assert;
import org.junit.Test;

public class ScannerCompiladorTest {

    @Test
    public void isVarTest(){
        ScannerCompilador scannerCompilador = new ScannerCompilador("var");
        ScannerCompilador scannerCompilador2 = new ScannerCompilador("VAR");
        Token token = scannerCompilador.obterProximoToken();
        Token token2 = scannerCompilador2.obterProximoToken();
        Assert.assertEquals(TipoToken.VAR, token.tipo);
        Assert.assertEquals(TipoToken.VAR, token2.tipo);
    }
    @Test
    public void isPrintTest(){
        ScannerCompilador scannerCompilador = new ScannerCompilador("print");
        ScannerCompilador scannerCompilador2 = new ScannerCompilador("PRINT");
        Token token = scannerCompilador.obterProximoToken();
        Token token2 = scannerCompilador2.obterProximoToken();
        Assert.assertEquals(TipoToken.PRINT, token.tipo);
        Assert.assertEquals(TipoToken.PRINT, token2.tipo);
    }
    @Test
    public void isID(){
        ScannerCompilador scannerCompilador = new ScannerCompilador("a");
        ScannerCompilador scannerCompilador2 = new ScannerCompilador("b");
        Token token = scannerCompilador.obterProximoToken();
        Token token2 = scannerCompilador2.obterProximoToken();
        Assert.assertEquals(TipoToken.ID, token.tipo);
        Assert.assertEquals(TipoToken.ID, token2.tipo);
    }
    @Test
    public void isIF(){
        ScannerCompilador scannerCompilador = new ScannerCompilador("if");
        ScannerCompilador scannerCompilador2 = new ScannerCompilador("IF");
        Token token = scannerCompilador.obterProximoToken();
        Token token2 = scannerCompilador2.obterProximoToken();
        Assert.assertEquals(TipoToken.IF, token.tipo);
        Assert.assertEquals(TipoToken.IF, token2.tipo);
    }

    @Test
    public void isOperadorRelacional(){
        ScannerCompilador scannerCompilador = new ScannerCompilador(">");
        ScannerCompilador scannerCompilador2 = new ScannerCompilador("<");
        ScannerCompilador scannerCompilador3 = new ScannerCompilador("!");
        Token token = scannerCompilador.obterProximoToken();
        Token token2 = scannerCompilador2.obterProximoToken();
        Token token3 = scannerCompilador3.obterProximoToken();
        Assert.assertEquals(TipoToken.MAIOR, token.tipo);
        Assert.assertEquals(TipoToken.MENOR, token2.tipo);
        Assert.assertEquals(TipoToken.NEGACAO, token3.tipo);
    }

    @Test
    public void isCondicional(){
        ScannerCompilador scannerCompilador = new ScannerCompilador("if");
        ScannerCompilador scannerCompilador2 = new ScannerCompilador("else");
        Token token = scannerCompilador.obterProximoToken();
        Token token2 = scannerCompilador2.obterProximoToken();
        Assert.assertEquals(TipoToken.IF, token.tipo);
        Assert.assertEquals(TipoToken.ELSE, token2.tipo);
    }

}
