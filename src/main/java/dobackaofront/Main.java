package dobackaofront;

import dobackaofront.controller.Banco;
import dobackaofront.model.Atendimento;
import dobackaofront.model.Endereco;
import dobackaofront.model.Paciente;
import dobackaofront.model.Telefone;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Banco b = new Banco();
        Connection conexao = b.conectar();

        ArrayList<Atendimento> atendimentos = b.listarTodosAtendimentos(conexao);

        for(int i=0; i<atendimentos.size(); i++) {
            Atendimento atendimento = atendimentos.get(i);
            System.out.println(atendimento.toString());
        }

        Atendimento atendimento = b.pesquisarAtendimentoPorId(1,conexao);
        Atendimento atendimento2 = new Atendimento();

        atendimento2.setId(atendimento.getId());
        atendimento2.setData(atendimento.getData());
        atendimento2.setPaciente_id(7);
        atendimento2.setMedico_id(5);

        b.atualizarAtendimento(atendimento2, conexao);

        b.desconectar(conexao);
    }
}