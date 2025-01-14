package dobackaofront;

import dobackaofront.controller.Banco;
import dobackaofront.model.Medico;
import dobackaofront.model.Paciente;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        Banco b = new Banco();
        Connection conexao = b.conectar();

        //b.executarScript(conexao);
        //b.executarBackup(conexao);


        //b.backupGeral(conexao);
        //b.desconectar(conexao);
        b.backupGeral(conexao);
    }
}