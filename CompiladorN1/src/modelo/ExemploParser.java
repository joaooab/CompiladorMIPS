package modelo;
public class ExemploParser {
    public static void main(String[] args) {
//    	"(5*3-4/(8+6)*9)+8*(4+7)"
        String sequencia = "2/(3-1)";
        Parser p = new Parser(sequencia);
        if(p.isExpressaoValida()){
        	System.out.println("Expressao OK!");
        }
    } 
}
