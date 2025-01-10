package dobackaofront.model;

import java.util.ArrayList;

public class Paciente {
    private Integer id;
    private String nome;
    private String cpf;
    private ArrayList<Telefone> telefones;
    private Endereco endereco;
    private ArrayList<Atendimento> atendimentos;

    public Paciente() {
        this.id = 0;
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = new ArrayList<>();
        this.endereco = new Endereco();
        this.atendimentos = new ArrayList<>();
    }

    public Paciente(String nome, String cpf) {
        this.id = 0;
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = new ArrayList<>();
        this.endereco = new Endereco();
        this.atendimentos = new ArrayList<>();
    }

    public Paciente(String nome, String cpf, String rua, String bairro, int numero) {
        this.id = 0;
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = new ArrayList<>();
        this.endereco = new Endereco(rua,bairro,numero);
        this.atendimentos = new ArrayList<>();
    }

    public Paciente(String nome, String cpf, String rua, String bairro, int numero, ArrayList<Telefone> telefones) {
        this.id = 0;
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = telefones;
        this.endereco = new Endereco(rua,bairro,numero);
        this.atendimentos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ArrayList<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(ArrayList<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void adicionarTelefone(String telefone) {
        Telefone t = new Telefone(telefone);
        telefones.add(t);
    }

    public void adicionarEndereco(String rua, String bairro, int numero) {
        this.endereco = new Endereco(rua,bairro,numero);
    }

    public ArrayList<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(ArrayList<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public void adicionarAtendimento(int medico_id, int paciente_id, String data) {
        Atendimento atendimento = new Atendimento(medico_id, paciente_id, data);
        atendimentos.add(atendimento);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefones=" + telefones +
                ", endereco=" + endereco +
                ", atendimentos=" + atendimentos +
                '}';
    }
}
