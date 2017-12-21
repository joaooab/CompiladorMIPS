package modelo;


public class Token {
    
    public TipoToken tipo;
    Object valor;
    
    public Token(TipoToken tipo, Object valor) {
        this.tipo = tipo;
        if (this.tipo == TipoToken.NUMERO || this.tipo == TipoToken.ID) {
            this.valor = valor;
        }
    }
    
    public String toString() {
        String texto = this.tipo.name();
        if (this.tipo == TipoToken.NUMERO) texto += ": " + this.valor;
        return texto;
    }

	public TipoToken getTipo() {
		return tipo;
	}
}

