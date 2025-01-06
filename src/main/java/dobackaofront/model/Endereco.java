package dobackaofront.model;

public class Endereco {
    private Integer id;
    private String rua;
    private String bairro;
    private Integer numero;

    public Endereco() {
        this.id = 0;
        this.rua = "";
        this.bairro = "";
        this.numero = 0;
    }

    public Endereco(String rua, String bairro, Integer numero) {
        this.id = 0;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", numero=" + numero +
                '}';
    }
}
