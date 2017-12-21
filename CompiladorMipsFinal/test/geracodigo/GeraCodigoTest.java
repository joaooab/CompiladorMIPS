package geracodigo;

import dao.GeraCodigo;
import dao.LeArquivo;
import modelo.Interpretador;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class GeraCodigoTest {

    @Test
    public void geraCodigoTest() throws FileNotFoundException, UnsupportedEncodingException {
        List<String> codigo = LeArquivo.getLinhasDeCodigo();
        Interpretador interpretador = new Interpretador(codigo);
        GeraCodigo.gera(interpretador.getCodigoMips());
    }
}
