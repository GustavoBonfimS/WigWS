package modelo;

/**
 *
 * @author gunai
 */
public class Empresa extends Usuario {
    private int idempresa;
    private String CNPJ;
    private String tipo;
    private String endereco;

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereo) {
        this.endereco = endereo;
    }
    
 
}
