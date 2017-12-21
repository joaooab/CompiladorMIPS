package modelo;

import java.util.*;

public class Interpretador {
    private String expressaoTxt = "";
    private ScannerCompilador scn;
    private Stack<Float> pilha = new Stack<Float>();
    private Map<String, Float> variaveis = new HashMap<>();
    private List<String> codigoMips = new ArrayList<>();
    private boolean eRestoif;

    public Interpretador(List<String> comandos) {
        for (String comando : comandos) {
            this.expressaoTxt += comando;
        }
        scn = new ScannerCompilador(expressaoTxt);
        inicializaCodigoMips();
        programa();
    }

    private void programa() {
        declaraVariaveis();
        comandos();
    }

    public List<String> getCodigoMips() {
        return codigoMips;
    }

    private void declaraVariaveis() {
        Token token = scn.obterProximoToken();
        if (token != null && token.tipo == TipoToken.VAR) {
            token = scn.obterProximoToken();
            if (token.tipo == TipoToken.ID) {
                declaraVariavelNaTabel(token);
                declaraVariavelCodigoMips(token.valor);
            } else {
                System.out.println("ERRO: ID esperado");
            }
            token = scn.obterProximoToken();
            if (token.tipo == TipoToken.PONTO_VIRGULA) {
                declaraVariaveis();
            } else {
                System.out.println("ERRO: Erro ponto e virgula esperado");
            }
        } else if (token != null && token.tipo == TipoToken.ID) {
            inicializaComandosMips();
            String posicoes = (String) token.valor;
            scn.voltaPosicoes(posicoes.length());
        } else {
            System.out.println("ERRO: inesperado na declaração de variaveis!");
        }
    }

    private void comandos() {
        Token token = scn.obterProximoToken();
        if (token != null && token.tipo == TipoToken.ID) {
            String valor = (String) token.valor;
            if (isVariavelDeclarada((String) token.valor)) {
                token = scn.obterProximoToken();
                if (token.tipo == TipoToken.ATRIBUICAO) {
                    expressao();
                    token = scn.obterProximoToken();
                    if (token.tipo == TipoToken.PONTO_VIRGULA) {
                        Float valorDaPilha = pilha.pop();
                        codigoMips.add("lw $t0, ($sp)");
                        codigoMips.add("addu $sp, $sp, 4");
                        codigoMips.add("sw $t0, " + valor);
                        declaraVariavelNaTabel(valor, valorDaPilha);
                        comandos();
                    } else {
                        System.out.println("ERRO: ponto e virgula esperado");
                    }
                } else {
                    System.out.println("ERRO: atribuicao esperado");
                }
            } else {
                System.out.println("ERRO: variavel nao foi declarada");
            }
        } else if (token != null && token.tipo == TipoToken.PRINT) {
            token = scn.obterProximoToken();
            if (token.tipo == TipoToken.ID) {
                String valor = (String) token.valor;
                if (isVariavelDeclarada(valor)) {
                    token = scn.obterProximoToken();
                    if (token.tipo == TipoToken.PONTO_VIRGULA) {
                        System.out.println(valor + ": " + variaveis.get(valor));
                        float valorDaVariavel = variaveis.get(valor);
                        codigoMips.add("#Imprimi");
                        codigoMips.add("li $t0, " + (int) valorDaVariavel);
                        codigoMips.add("sw $t0, a");
                        codigoMips.add("li $v0, 1");
                        codigoMips.add("lw $a0, a");
                        codigoMips.add("syscall");
                        comandos();
                    } else {
                        System.out.println("ERRO: ponto e virgula esperado");
                    }
                } else {
                    System.out.println("ERRO: variavel nao foi declarada");
                }
            } else {
                System.out.println("ERRO: ID esperado");
            }
        } else if (token != null && token.tipo == TipoToken.IF) {
            token = scn.obterProximoToken();
            if (token != null && token.tipo == TipoToken.ABRE_PARENTESES) {
                boolean expressaoLogica = expressaoLogica();
                token = scn.obterProximoToken();
                if (token.tipo == TipoToken.FECHA_PARENTESES) {
                    token = scn.obterProximoToken();
                    if (token.tipo == TipoToken.ABRE_CHAVES) {
                        if (expressaoLogica) {
                            comandos();
                        } else {
                            resto_IF();
                        }
                        token = scn.obterProximoToken();
                        if (token.tipo == TipoToken.FECHA_CHAVES) {
                            comandos();
                        } else {
                            System.out.println("ERRO: fecha chaves");
                        }
                    } else {
                        System.out.println("ERRO: abre chaves");
                    }
                } else {
                    System.out.println("ERRO: fecha parenteses esperado");
                }
            } else {
                System.out.println("ERRO: parenteses esperado");
            }
        } else if (token != null && token.tipo == TipoToken.ELSE) {
            if (eRestoif) {
                token = scn.obterProximoToken();
                if (token.tipo == TipoToken.ABRE_CHAVES) {
                    comandos();
                    token = scn.obterProximoToken();
                    if (token.tipo == TipoToken.FECHA_CHAVES) {
                        eRestoif = false;
                        comandos();
                    }
                } else {
                    System.out.println("ERRO: abre chaves esperado");
                }
            } else {
                System.out.println("ERRO: necessário if antes do else");
            }
        } else if (token != null && token.tipo == TipoToken.FECHA_CHAVES) {
            scn.voltaPosicao();
        } else if (token != null) {
            System.out.println("ERRO: inesperado na execução de comandos ");
        }
    }

    private void resto_IF() {
        Token token = scn.obterProximoToken();
        while (token != null && token.tipo != TipoToken.FECHA_CHAVES) {
            token = scn.obterProximoToken();
        }
        eRestoif = true;
        scn.voltaPosicao();
    }

    private boolean expressaoLogica() {
        float valor1 = 0;
        float valor2 = 0;
        String operador = "";
        Token token = scn.obterProximoToken();
        if (token != null && token.tipo == TipoToken.ID) {
            if (isVariavelDeclarada((String) token.valor)) {
                valor1 = variaveis.get(token.valor);
                operador = getOperador();
                token = scn.obterProximoToken();
                if (token != null && token.tipo == TipoToken.NUMERO) {
                    valor2 = Float.valueOf(token.valor.toString());
                } else if (token != null && token.tipo == TipoToken.ID) {
                    if (isVariavelDeclarada((String) token.valor)) {
                        valor2 = variaveis.get(token.valor);
                    } else {
                        System.out.println("ERRO: variavel não foi declarada");
                    }
                } else {
                    System.out.println("ERRO: numero ou variavel esperado");
                }
            } else {
                System.out.println("ERRO: variavel nao foi delcarada");
            }
        } else if (token != null && token.tipo == TipoToken.NUMERO) {
            valor1 = Float.valueOf(token.valor.toString());
            operador = getOperador();
            token = scn.obterProximoToken();
            if (token != null && token.tipo == TipoToken.ID) {
                if (isVariavelDeclarada((String) token.valor)) {
                    valor2 = variaveis.get(token.valor);
                } else {
                    System.out.println("ERRO: variavel não foi declarada");
                }
            } else {
                System.out.println("ERRO: ID esperado");
            }
        } else {
            System.out.println("ERRO: ID ou número esperado");
        }
        return executaExpressaoLogica(valor1, operador, valor2);
    }

    private boolean executaExpressaoLogica(float valor1, String operador, float valor2) {
        switch (operador) {
            case ">":
                return valor1 > valor2;
            case "<":
                return valor1 < valor2;
            case ">=":
                return valor1 >= valor2;
            case "<=":
                return valor1 <= valor2;
            case "!=":
                return valor1 != valor2;
            default:
                System.out.println("ERRO: operador não encontrado");
                return false;
        }
    }

    private String getOperador() {
        String operador = "";
        Token token = scn.obterProximoToken();
        if (token != null && token.tipo == TipoToken.MAIOR) {
            operador = ">";
        } else if (token != null && token.tipo == TipoToken.MENOR) {
            operador = "<";
        } else if (token != null && token.tipo == TipoToken.ATRIBUICAO) {
            operador = "=";
        } else if (token != null && token.tipo == TipoToken.NEGACAO) {
            operador = "!";
        } else {
            System.out.println("ERRO: operador esperado");
        }
        token = scn.obterProximoToken();
        if (token != null && token.tipo == TipoToken.ATRIBUICAO) {
            operador += "=";
        } else {
            scn.voltaPosicao();
        }
        return operador;
    }

    private void expressao() {
        if (termo()) {
            restante1();
        } else {
            System.out.println("ERRO: Expressão não inicia com NUMERO");
        }
    }

    private Boolean termo() {
        if (fator()) {
            restante2();
            return true;
        } else {
            return false;
        }
    }

    private boolean fator() {
        Token token = scn.obterProximoToken();
        if (token != null && token.getTipo() == TipoToken.ABRE_PARENTESES) {
            expressao();
            token = scn.obterProximoToken();
            return token != null && token.getTipo() == TipoToken.FECHA_PARENTESES;
        } else if (token != null && token.getTipo() == TipoToken.NUMERO) {
            pilha.push(Float.valueOf(token.valor.toString()));
            codigoMips.add("li $t3, " + Float.valueOf(token.valor.toString()).intValue());
            codigoMips.add("subu $sp, $sp, 4");
            codigoMips.add("sw $t3, ($sp)");
            return true;
        } else if (token != null && token.getTipo() == TipoToken.ID) {
            if (isVariavelDeclarada((String) token.valor)) {
                Float valorDaPilha = variaveis.get(token.valor);
                pilha.push(valorDaPilha);
                return true;
            }
        }

        pilha.push(Float.valueOf(token.valor.toString()));
        return true;
    }

    private void restante1() {
        Token token = scn.obterProximoToken();

        if (token != null && token.getTipo() == TipoToken.SOMA) {
            if (termo()) {
                float b = pilha.pop();
                float a = pilha.pop();
                pilha.push(a + b);

                escreveOperacaoMips("add");
                restante1();
            }
        } else if (token != null && token.getTipo() == TipoToken.SUB) {
            if (termo()) {
                float b = pilha.pop();
                float a = pilha.pop();
                pilha.push(a - b);

                escreveOperacaoMips("sub");
                restante1();
            }
            restante1();
        } else if (token != null && token.getTipo() == TipoToken.ABRE_PARENTESES) {
            if (termo()) {
                restante1();
            }
        } else {
            if (token != null) {
                scn.voltaPosicao();
            }
        }
    }

    private void restante2() {
        Token token = scn.obterProximoToken();

        if (token != null && token.getTipo() == TipoToken.MUL) {
            if (fator()) {
                float b = pilha.pop();
                float a = pilha.pop();
                pilha.push(a * b);

                escreveOperacaoMips("mul");
                restante2();
                restante1();
            }
        } else if (token != null && token.getTipo() == TipoToken.DIV) {
            if (fator()) {
                float b = pilha.pop();
                float a = pilha.pop();
                pilha.push(a / b);

                escreveOperacaoMips("div");
                restante2();
                restante1();
            }
        } else if (token != null && token.getTipo() == TipoToken.FECHA_PARENTESES) {
            scn.voltaPosicao();
        } else {
            if (token != null) {
                scn.voltaPosicao();
            }
        }
    }

    private void declaraVariavelNaTabel(Token token) {
        variaveis.put((String) token.valor, (float) 0);
    }

    private void declaraVariavelNaTabel(String valor, float valorPilha) {
        variaveis.put(valor, valorPilha);
    }

    private void inicializaCodigoMips() {
        codigoMips.add(".data");
    }

    private void inicializaComandosMips() {
        codigoMips.add(".text");
        codigoMips.add(".globl main");
        codigoMips.add("main:");
    }

    private void declaraVariavelCodigoMips(Object tokenId) {
        String id = (String) tokenId;
        codigoMips.add(id + ": .word 0");
    }

    private boolean isVariavelDeclarada(String variavel) {
        return variaveis.containsKey(variavel);
    }

    private void escreveOperacaoMips(String operacao) {
        codigoMips.add("#DIV");

        codigoMips.add("lw $t1, ($sp)");
        codigoMips.add("addu $sp, $sp, 4");

        codigoMips.add("lw $t0, ($sp)");
        codigoMips.add("addu $sp, $sp, 4");

        codigoMips.add(operacao + " $t0, $t0, $t1");

        codigoMips.add("subu $sp, $sp, 4");
        codigoMips.add("sw $t0, ($sp)");
    }
}
