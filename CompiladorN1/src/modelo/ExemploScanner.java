package modelo;

public class ExemploScanner {

    public static void main(String[] args) {
        String sequencia = ("20 * 4 / 2 + 3");
        sequencia += "\0";
        ScannerCompilador scn = new ScannerCompilador(sequencia);
        Token t = scn.obterProximoToken();
        while ( t != null) {
            System.out.println(t);
            t = scn.obterProximoToken();
        }
    }
}
    
