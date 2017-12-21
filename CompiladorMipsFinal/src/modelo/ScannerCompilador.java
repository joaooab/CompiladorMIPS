package modelo;

public class ScannerCompilador {
    String entrada;
    int posicao = 0;

    public ScannerCompilador(String entrada) {
        this.entrada = entrada;
    }

    public void voltaPosicao() {
        --posicao;
    }

    public Token obterProximoToken() {

        if (posicao >= entrada.length()) {
            return null;
        }

        while (entrada.charAt(posicao) == ' ') {
            posicao++;
        }

        if (isDigit()) {
            return new Token(TipoToken.NUMERO, getDigit());
        } else if (entrada.charAt(posicao) == '+') {
            posicao++;
            return new Token(TipoToken.SOMA, null);
        } else if (entrada.charAt(posicao) == '-') {
            posicao++;
            return new Token(TipoToken.SUB, null);
        } else if (entrada.charAt(posicao) == '*') {
            posicao++;
            return new Token(TipoToken.MUL, null);
        } else if (entrada.charAt(posicao) == '/') {
            posicao++;
            return new Token(TipoToken.DIV, null);
        } else if (entrada.charAt(posicao) == '(') {
            posicao++;
            return new Token(TipoToken.ABRE_PARENTESES, null);
        } else if (entrada.charAt(posicao) == ')') {
            posicao++;
            return new Token(TipoToken.FECHA_PARENTESES, null);
        } else if (entrada.charAt(posicao) == '=') {
            posicao++;
            return new Token(TipoToken.ATRIBUICAO, null);
        } else if (entrada.charAt(posicao) == ';') {
            posicao++;
            return new Token(TipoToken.PONTO_VIRGULA, null);
        } else if (entrada.charAt(posicao) == '!') {
            posicao++;
            return new Token(TipoToken.NEGACAO, null);
        } else if (entrada.charAt(posicao) == '>') {
            posicao++;
            return new Token(TipoToken.MAIOR, null);
        } else if (entrada.charAt(posicao) == '<') {
            posicao++;
            return new Token(TipoToken.MENOR, null);
        } else if (entrada.charAt(posicao) == '{') {
            posicao++;
            return new Token(TipoToken.ABRE_CHAVES, null);
        } else if (entrada.charAt(posicao) == '}') {
            posicao++;
            return new Token(TipoToken.FECHA_CHAVES, null);
        } else if (isVar()) {
            return new Token(TipoToken.VAR, null);
        } else if (isPrint()) {
            return new Token(TipoToken.PRINT, null);
        } else if (isIF()) {
            return new Token(TipoToken.IF, null);
        } else if (isElse()) {
            return new Token(TipoToken.ELSE, null);
        } else if (isID()) {
            return new Token(TipoToken.ID, getID());
        } else {
            posicao++;
            return new Token(TipoToken.ERRO, null);
        }
    }

    private boolean isVar() {
        int posicoes = 0;
        if (entrada.toUpperCase().charAt(posicao) == 'V') {
            posicao++;
            posicoes++;
            if (entrada.toUpperCase().charAt(posicao) == 'A') {
                posicao++;
                posicoes++;
                if (entrada.toUpperCase().charAt(posicao) == 'R') {
                    posicao++;
                    return true;
                }
            }
        }
        voltaPosicoes(posicoes);
        return false;
    }

    private boolean isPrint() {
        int posicoes = 0;
        if (entrada.toUpperCase().charAt(posicao) == 'P') {
            posicao++;
            posicoes++;
            if (entrada.toUpperCase().charAt(posicao) == 'R') {
                posicao++;
                posicoes++;
                if (entrada.toUpperCase().charAt(posicao) == 'I') {
                    posicao++;
                    posicoes++;
                    if (entrada.toUpperCase().charAt(posicao) == 'N') {
                        posicao++;
                        posicoes++;
                        if (entrada.toUpperCase().charAt(posicao) == 'T') {
                            posicao++;
                            return true;
                        }
                    }
                }
            }
        }
        voltaPosicoes(posicoes);
        return false;
    }

    public void voltaPosicoes(int posicoes) {
        for (int i = 0; i < posicoes; i++) {
            voltaPosicao();
        }
    }

    private Boolean isID() {
        return Character.isAlphabetic(entrada.charAt(posicao));
    }

    private String getID() {
        String valorStr = "" + entrada.charAt(posicao);
        posicao++;
        while (posicao < entrada.length() && (Character.isAlphabetic(entrada.charAt(posicao))
                || Character.isDigit(entrada.charAt(posicao)))) {
            valorStr += entrada.charAt(posicao);
            posicao++;
        }
        return valorStr;
    }

    private Boolean isDigit() {
        return Character.isDigit(entrada.charAt(posicao));
    }

    private Integer getDigit() {
        String valorStr = "" + entrada.charAt(posicao);
        posicao++;
        while (posicao < entrada.length() && Character.isDigit(entrada.charAt(posicao))) {
            valorStr += entrada.charAt(posicao);
            posicao++;
        }
        return Integer.parseInt(valorStr);
    }

    private boolean isIF() {
        int posicoes = 0;
        if (entrada.toUpperCase().charAt(posicao) == 'I') {
            posicao++;
            posicoes++;
            if (entrada.toUpperCase().charAt(posicao) == 'F') {
                posicao++;
                return true;
            }
        }
        voltaPosicoes(posicoes);
        return false;
    }

    private boolean isElse() {
        int posicoes = 0;
        if (entrada.toUpperCase().charAt(posicao) == 'E') {
            posicao++;
            posicoes++;
            if (entrada.toUpperCase().charAt(posicao) == 'L') {
                posicao++;
                posicoes++;
                if (entrada.toUpperCase().charAt(posicao) == 'S') {
                    posicao++;
                    posicoes++;
                    if (entrada.toUpperCase().charAt(posicao) == 'E') {
                        posicao++;
                        return true;
                    }
                }
            }
        }
        voltaPosicoes(posicoes);
        return false;
    }

    private boolean isWhile() {
        int posicoes = 0;
        if (entrada.toUpperCase().charAt(posicao) == 'W') {
            posicao++;
            posicoes++;
            if (entrada.toUpperCase().charAt(posicao) == 'H') {
                posicao++;
                posicoes++;
                if (entrada.toUpperCase().charAt(posicao) == 'I') {
                    posicao++;
                    posicoes++;
                    if (entrada.toUpperCase().charAt(posicao) == 'L') {
                        posicao++;
                        if (entrada.toUpperCase().charAt(posicao) == 'E') {
                            posicao++;
                            return true;
                        }
                    }
                }
            }
        }
        voltaPosicoes(posicoes);
        return false;
    }
}
