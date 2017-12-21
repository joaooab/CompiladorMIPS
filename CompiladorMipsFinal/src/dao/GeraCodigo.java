package dao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class GeraCodigo {

    public static void gera(final List<String> linhas) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter escreve = new PrintWriter("codigoMips.asm", "UTF-8");

        for (String linha : linhas) {
            escreve.println(linha);
        }

        escreve.close();
    }
}
