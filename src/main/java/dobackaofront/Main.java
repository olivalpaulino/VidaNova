package dobackaofront;

import dobackaofront.controller.Banco;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Banco b = new Banco();
        Connection conexao = b.conectar();
        b.desconectar(conexao);


    }

}