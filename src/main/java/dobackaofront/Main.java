package dobackaofront;

import dobackaofront.controller.Banco;
import dobackaofront.model.Endereco;
import dobackaofront.model.Medico;
import dobackaofront.model.Paciente;
import dobackaofront.model.Telefone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Banco b = new Banco();
        Connection conexao;
        Main m = new Main();
        //b.executarScript(conexao); //b.executarBackup(conexao); //b.backupGeral(conexao);   //b.desconectar(conexao);
        // b.backupGeral(conexao);

        int opcao = 1;
        Scanner sc;
        while(opcao != 0) {
            System.out.println("1-Cadastramento de Médicos\n2-Cadastramento de Paciente\n3-Cadastramento de Atendimento\n0-Sair");
            sc = new Scanner(System.in);
            opcao = sc.nextInt();

            if (opcao == 1) {
                conexao = b.conectar();
                m.cadastrarMedico(b,conexao);
                conexao.close();
            } else if (opcao == 2) {
                conexao = b.conectar();
                m.cadastrarPaciente(b,conexao);
                b.desconectar(conexao);
            } else if (opcao == 3) {
                //m.cadastrarAtendimento(b,conexao);
            } else if(opcao == 0) {
                System.out.println("O Programa foi finalizado com sucesso!");
            }
        }
    }

    public void cadastrarMedico(Banco b, Connection conexao) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Insira o Nome do médico: ");
        String nome = sc.nextLine();

        System.out.println("Insira o CRM do médico: ");
        String crm = sc.nextLine();

        Medico medico = new Medico(nome,crm);

        System.out.println(medico.toString());
        b.adicionar(medico, conexao);
    }

    public void cadastrarPaciente(Banco b, Connection conexao) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Insira o Nome do paciente: ");
        String nome = sc.nextLine();

        System.out.println("Insira o Cpf do paciente: ");
        String cpf = sc.nextLine();

        Paciente paciente = new Paciente(nome, cpf);

        System.out.println("Informe a rua do paciente: ");
        String rua = sc.nextLine();

        System.out.println("Informe o bairro do paciente: ");
        String bairro = sc.nextLine();

        System.out.println("Informe numero da casa do paciente: ");
        int numeroCasa = sc.nextInt();

        Endereco endereco = new Endereco(rua, bairro, numeroCasa);

        System.out.println("Deseja cadastrar um telefone para contato? 0-Não, 1-SIM");
        int opcao = sc.nextInt();
        ArrayList<Telefone> telefones = new ArrayList<>();

        if (opcao == 1) {
            while(opcao != 0) {
                System.out.println("Informe o numero do telefone do paciente: ");
                sc = new Scanner(System.in);
                String numeroTelefone = sc.nextLine();

                Telefone telefone = new Telefone(numeroTelefone);
                telefones.add(telefone);

                System.out.println("Deseja cadastrar um novo telefone para contato? 0-Não, 1-SIM");
                sc = new Scanner(System.in);
                opcao = sc.nextInt();
            }

            b.adicionar(paciente,endereco,telefones,conexao);
        } else {
            Telefone telefone = new Telefone("");
            b.adicionar(paciente,endereco,telefones,conexao);
        }
    }
}