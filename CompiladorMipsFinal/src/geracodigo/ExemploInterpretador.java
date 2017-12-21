package geracodigo;

import dao.GeraCodigo;
import dao.LeArquivo;
import modelo.Interpretador;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ExemploInterpretador {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        List<String> codigo = LeArquivo.getLinhasDeCodigo();
        Interpretador interpretador = new Interpretador(codigo);
        GeraCodigo.gera(interpretador.getCodigoMips());
    }
}
