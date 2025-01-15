package dobackaofront;

import dobackaofront.controller.Banco;
import dobackaofront.model.Medico;
import dobackaofront.model.Paciente;

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
                //m.cadastrarPaciente(b,conexao);
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
}