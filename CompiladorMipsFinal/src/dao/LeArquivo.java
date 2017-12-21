package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeArquivo {

    public static List<String> getLinhasDeCodigo() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("comandos.txt"));
        List<String> linhas = new ArrayList<>();
        while (sc.hasNext()) {
            linhas.add(sc.nextLine());
        }
        return linhas;
    }
}
