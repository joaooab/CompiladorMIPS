package test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import modelo.InterpretadorExpressao;
import modelo.Parser;

public class ExemploInterpretadorTest {

	@Test
	public void getResultadoOperacoesBasicas() {
		InterpretadorExpressao i1 = new InterpretadorExpressao("2+2");
		Assert.assertEquals(4.0, i1.getResultado(), 0.001);
		InterpretadorExpressao i2 = new InterpretadorExpressao("2*3");
		Assert.assertEquals(6.0, i2.getResultado(), 0.001);
		InterpretadorExpressao i3 = new InterpretadorExpressao("2-4");
		Assert.assertEquals(-2.0, i3.getResultado(), 0.001);
		InterpretadorExpressao i4 = new InterpretadorExpressao("2/2");
		Assert.assertEquals(1.0, i4.getResultado(), 0.001);
		InterpretadorExpressao i5 = new InterpretadorExpressao("3-2+1");
		Assert.assertEquals(2.0, i5.getResultado(), 0.001);
		InterpretadorExpressao i6 = new InterpretadorExpressao("2*(3-1)");
		Assert.assertEquals(4.0, i6.getResultado(), 0.001);
		InterpretadorExpressao i7 = new InterpretadorExpressao("2/(3-1)");
		Assert.assertEquals(1.0, i7.getResultado(), 0.001);
	}
}
