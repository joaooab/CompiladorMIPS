package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import modelo.Analisador;
import modelo.ScannerCompilador;
import modelo.TipoToken;
import modelo.Token;

public class ExemploAnalisadorTest {
	@Test
	public void varificaLeitruaDeVariavel() throws FileNotFoundException {
		Scanner sc = new Scanner(new FileReader("comandos.txt"));
		String expressaoTxt = sc.nextLine();
		ScannerCompilador scn = new ScannerCompilador(expressaoTxt);
		Token token = scn.obterProximoToken();
		Assert.assertEquals(TipoToken.VAR, token.getTipo());
	}
	
	@Test
	public void verificaLeituraID(){
		ScannerCompilador scn = new ScannerCompilador("a123");
		ScannerCompilador scn2 = new ScannerCompilador("va23");
		Token token = scn.obterProximoToken();
		Token token2 = scn2.obterProximoToken();
		Assert.assertEquals(TipoToken.ID, token.getTipo());
		Assert.assertEquals(TipoToken.ID, token2.getTipo());
	}
	
	@Test
	public void verificaComando(){
		Analisador analisador = new Analisador("nome = 10+3*(2+1);");
		Analisador analisador2 = new Analisador("nome = +10+3*(2+1);");
		Analisador analisador3 = new Analisador("nome = ;10+3*(2+1)");
		Analisador analisador4 = new Analisador("naoInicializada = 2");
		analisador.inicializaNaTable("nome");
		analisador2.inicializaNaTable("nome");
		analisador3.inicializaNaTable("nome");
		Assert.assertTrue(analisador.comando());
		Assert.assertFalse(analisador2.comando());
		Assert.assertFalse(analisador3.comando());
		Assert.assertFalse(analisador4.comando());
	}
}
