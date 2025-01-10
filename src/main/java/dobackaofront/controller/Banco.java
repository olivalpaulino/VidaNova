package dobackaofront.controller;

import dobackaofront.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Banco {
    // usario, senha, url=localhost:3306/vidanova
    private String usuario;
    private String senha;
    private String url;

    public Banco() {

    }

    public Connection conectar() {
        usuario="root";
        senha="root";
        url="jdbc:mysql://localhost:3306";

        Connection conectado = null;

        try {
            conectado = DriverManager.getConnection(url,usuario,senha);
            System.out.println("Banco de Dados conectado com Sucesso!");
        } catch (SQLException e) {
            System.out.println("Não consegui conectar ao banco com estas informacoes!");
        }

        return conectado;
    }

    public void desconectar(Connection conexao) throws SQLException {
        conexao.close();
    }

    public void adicionar(Medico medico, Connection conexao) {
        String sql = "insert into medico(nome,crm) values(?,?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1,medico.getNome());
            stmt.setString(2, medico.getCrm());

            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas > 0) {
                System.out.println("Médico foi cadastrado com sucesso!");
            }

            stmt.close();
            conexao.close();
        } catch(SQLException e) {
            System.out.println("Aconteceu um erro ao tentar adicionar o medico no banco de dados!");
        }
    }

    public void atualizar(Medico medico, Connection conexao) {
        String sql = "update medico set nome = ?, crm = ? where id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setInt(3, medico.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Médico foi atualizado com Sucesso!");
            } else {
                System.out.println("Nenhum médico foi encontrado com o ID informado!");
            }
        } catch(SQLException e){
            System.out.println("Aconteceu um erro ao tentar atualizar o medico no banco de dados!");
            e.printStackTrace();
        }
    }

    public ArrayList<Medico> pesquisarTodosMedicos(Connection conexao) {
        ArrayList<Medico> medicos = new ArrayList<>();
        String sql = "select id,nome,crm from medico";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Medico medico = new Medico();

                // rs.getTipoDoDado("nome da coluna");
                medico.setId(rs.getInt("id"));
                medico.setNome(rs.getString("nome"));
                medico.setCrm(rs.getString("crm"));

                medicos.add(medico);
            }
            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.out.println("Erro ao buscar os cadastros dos médicos!");
            e.printStackTrace();
        }
        return medicos;
    }

    public void deletarMedico(int id, Connection conexao) {
        String sql = "delete from medico where id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhaAfetadas = stmt.executeUpdate();

            if (linhaAfetadas > 0) {
                System.out.println("Médico foi deletado com sucesso!");
            } else {
                System.out.println("Nenhum médico encontrado com o ID fornecido!");
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Não foi possível deletarMedico pelo ID");
            e.printStackTrace();
        }
    }

    public void deletarMedico(String crm, Connection conexao) {
        String sql = "delete from medico where crm = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, crm);

            int linhaAfetadas = stmt.executeUpdate();

            if (linhaAfetadas > 0) {
                System.out.println("Médico foi deletado com sucesso!");
            } else {
                System.out.println("Nenhum médico encontrado com o ID fornecido!");
            }

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Não foi possível deletarMedico pelo ID");
            e.printStackTrace();
        }
    }

    public Medico pesquisarMedico(String crm, Connection conexao) {
        String sql = "Select id,nome,crm from medico where crm = ?";
        Medico medico = new Medico();

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, crm);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String crm2 = rs.getString("crm");
                medico.setId(id);
                medico.setNome(nome);
                medico.setCrm(crm2);
            }
            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.out.println("Erro ao pesquisar médico pelo CRM: "+e.getMessage());
            e.printStackTrace();
        }
        return medico;
    }

    public void adicionar(Paciente paciente, Endereco endereco, ArrayList<Telefone> telefones, Connection conexao) {
        String sql = "insert into paciente(nome,cpf) values(?,?)";

        try {
            // Parte 1 - Cadastramento do Paciente

            PreparedStatement stmtPaciente = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtPaciente.setString(1,paciente.getNome());
            stmtPaciente.setString(2,paciente.getCpf());

            stmtPaciente.executeUpdate();

            ResultSet rsPaciente = stmtPaciente.getGeneratedKeys();
            int pacienteId = 0;

            if(rsPaciente.next()) {
                pacienteId = rsPaciente.getInt(1);
            }

            rsPaciente.close();
            stmtPaciente.close();

            // Parte 2 - Cadastrando o Endereco usando a Chave Estrangeira de Paciente
            String sqlEndereco = "insert into endereco(paciente_id,numero,bairro,rua) values(?,?,?,?)";

            PreparedStatement stmtEndereco = conexao.prepareStatement(sqlEndereco);
            stmtEndereco.setInt(1,pacienteId);
            stmtEndereco.setInt(2,endereco.getNumero());
            stmtEndereco.setString(3,endereco.getBairro());
            stmtEndereco.setString(4,endereco.getRua());

            stmtEndereco.executeUpdate();
            stmtEndereco.close();

            // Parte 3 - Cadastrando os Telefone de Contato usando a Chave Estrangeira de Paciente
            String sqlTelefone = "insert into telefone(paciente_id, numero) values(?,?)";

            for (int i=0; i<telefones.size(); i++) {
                Telefone telefone = telefones.get(i);

                PreparedStatement stmtTelefone = conexao.prepareStatement(sqlTelefone);
                stmtTelefone.setInt(1,pacienteId);
                stmtTelefone.setString(2, telefone.getNumero());

                stmtTelefone.executeUpdate();
                stmtTelefone.close();
            }

            System.out.println("Paciente foi cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Não foi possível inserir o paciente no banco de dados! - "+e.getMessage());
            e.printStackTrace();
        }
    }

    public void adicionar(Endereco endereco, Connection conexao) {
        // codigo mysql atraves do java
    }

    public void adicionar(Telefone telefone, Connection conexao) {
        // codigo mysql atraves do java
    }

    public void adicionar(Atendimento atendimento, Connection conexao) {
        // codigo mysql atraves do java
    }

    public Paciente pesquisar(String cpf, Connection conexao) {
        String sql = "select p.id, p.nome, p.cpf, e.rua, e.bairro, e.numero, t.numero as telefone, t.id as codigo from paciente p " +
                "join endereco e on p.id = e.paciente_id join telefone t on p.id = t.paciente_id where cpf = ?";
        Paciente p = new Paciente();

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            Endereco e = new Endereco();
            Telefone t;
            ArrayList<Telefone> telefones = new ArrayList<>();

            while(rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));

                e.setRua(rs.getString("rua"));
                e.setBairro(rs.getString("bairro"));
                e.setNumero(rs.getInt("numero"));

                t = new Telefone(rs.getString("telefone"));
                t.setId(rs.getInt("codigo"));
                telefones.add(t);
            }

            rs.close();
            stmt.close();

            e.setId(p.getId());
            p.setEndereco(e);

            p.setTelefones(telefones);
        } catch(SQLException e) {
            System.out.println("Erro ao pesquisar o paciente pelo cpf!");
            e.printStackTrace();
        }
        return p;
    }

    public ArrayList<Paciente> pesquisarTodosPacientes(Connection conexao) {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        String sqlPaciente = "select id,nome,cpf from paciente";
        String sqlEndereco = "select id, paciente_id, numero, bairro, rua from endereco where paciente_id = ?";
        String sqlTelefone = "select id, paciente_id, telefone where paciente_id = ?";

        try {
            PreparedStatement stmtPaciente = conexao.prepareStatement(sqlPaciente);
            ResultSet rsPaciente = stmtPaciente.executeQuery();

            PreparedStatement stmtEnderco = conexao.prepareStatement(sqlEndereco);
            ResultSet rsEndereco = null;

            PreparedStatement stmtTelefone = conexao.prepareStatement(sqlTelefone);
            ResultSet rsTelefone = null;

            while(rsPaciente.next()) {
                Paciente paciente = new Paciente();

                // rs.getTipoDoDado("nome da coluna");
                paciente.setId(rsPaciente.getInt("id"));
                paciente.setNome(rsPaciente.getString("nome"));
                paciente.setCpf(rsPaciente.getString("cpf"));

                stmtEnderco.setInt(1,paciente.getId());
                rsEndereco = stmtEnderco.executeQuery();

                Endereco endereco = new Endereco();
                endereco.setId(rsEndereco.getInt("paciente_id"));
                endereco.setRua(rsEndereco.getString("rua"));
                endereco.setBairro(rsEndereco.getString("bairro"));
                endereco.setNumero(rsEndereco.getInt("numero"));

                paciente.setEndereco(endereco);

                stmtTelefone.setInt(1,paciente.getId());
                rsTelefone = stmtTelefone.executeQuery();

                ArrayList<Telefone> telefones = new ArrayList<>();
                while(rsTelefone.next()) {
                    Telefone telefone = new Telefone(rsTelefone.getString("numero"));
                    telefones.add(telefone);
                }

                paciente.setTelefones(telefones);

                pacientes.add(paciente);
            }
            rsTelefone.close();
            rsEndereco.close();
            rsPaciente.close();

            stmtPaciente.close();
            stmtEnderco.close();
            stmtTelefone.close();
        } catch(SQLException e) {
            System.out.println("Erro ao buscar os cadastros dos médicos!");
            e.printStackTrace();
        }
        return pacientes;
    }

    public void atualizar(Paciente paciente_novo, Connection conexao) {

        String sqlPaciente = "update paciente set nome = ?, cpf = ? where cpf = ?";
        String sqlEndereco = "update endereco set rua = ?, bairro = ?, numero = ? where paciente_id = ?";
        String sqlTelefone = "update telefone set numero = ? where paciente_id = ? and id = ?";

        try {
            // 1 atualizacao dos dados do paciente
            PreparedStatement stmtPaciente = conexao.prepareStatement(sqlPaciente);
            stmtPaciente.setString(1, paciente_novo.getNome());
            stmtPaciente.setString(2,paciente_novo.getCpf());
            stmtPaciente.setString(3,paciente_novo.getCpf());

            stmtPaciente.executeUpdate();

            System.out.println(paciente_novo.getEndereco().toString()+"-------------------------------");
            System.out.println("ID do Paciente Novo: "+paciente_novo.getId());
            // 2 atualizacao dos dados do endereco
            PreparedStatement stmtEndereco = conexao.prepareStatement(sqlEndereco);
            stmtEndereco.setString(1, paciente_novo.getEndereco().getRua());
            stmtEndereco.setString(2, paciente_novo.getEndereco().getBairro());
            stmtEndereco.setInt(3, paciente_novo.getEndereco().getNumero());
            stmtEndereco.setInt(4, paciente_novo.getEndereco().getId());

            stmtEndereco.executeUpdate();
            stmtEndereco.close();

            // 3 atualizacao dos dados do telefone

            ArrayList<Telefone> telefones = paciente_novo.getTelefones();
            for (int i=0; i<telefones.size(); i++) {
                Telefone t = telefones.get(i);
                System.out.println(t.toString()+" --------------------- TELEFONE");

                PreparedStatement stmtTelefone = conexao.prepareStatement(sqlTelefone);
                stmtTelefone.setString(1, t.getNumero());
                stmtTelefone.setInt(2, paciente_novo.getId());
                stmtTelefone.setInt(3, t.getId());

                stmtTelefone.executeUpdate();
                stmtTelefone.close();
            }


            stmtPaciente.close();

            System.out.println("Dados do Paciente foi atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Não foi possível atualizar os dados do paciente!");
            e.printStackTrace();
        }
    }

    public void deletarPaciente(String cpf, Connection conexao) {
        Paciente paciente = pesquisar(cpf,conexao);
        int chaveEstrangeiraDoPaciente = paciente.getId();

        // delecao dos telefones
        String sqlTelefone = "delete from telefone where paciente_id = ?";
        String sqlEndereco = "delete from endereco where paciente_id = ?";
        String sqlPaciente = "delete from paciente where cpf = ?";

        try {
            PreparedStatement stmtTelefone = conexao.prepareStatement(sqlTelefone);
            stmtTelefone.setInt(1,chaveEstrangeiraDoPaciente);
            stmtTelefone.executeUpdate();
            stmtTelefone.close();

            System.out.println("Todos os contatos deste paciente foram deletedos: "+paciente.getNome());

            // delecao do endereco do paciente
            PreparedStatement stmtEndereco = conexao.prepareStatement(sqlEndereco);
            stmtEndereco.setInt(1,chaveEstrangeiraDoPaciente);
            stmtEndereco.executeUpdate();
            stmtEndereco.close();

            System.out.println("O endereço deste paciente foi deletedo: "+paciente.getNome());

            // delecao do paciente em si
            PreparedStatement stmtPaciente = conexao.prepareStatement(sqlPaciente);
            stmtPaciente.setString(1,paciente.getCpf());
            stmtPaciente.executeUpdate();
            stmtPaciente.close();

            System.out.println("O paciente foi deletado com sucesso: "+paciente.getNome());
        } catch (SQLException e) {
            System.out.println("Não foi possível deleter os telefones do paciente!");
            e.printStackTrace();
        }
    }

    public void adicionarAtendimento(String crm, String cpf, String dataDeAtendimento, Connection conexao) {
        Medico medico = pesquisarMedico(crm,conexao);
        Paciente paciente = pesquisar(cpf,conexao);

        int idMedico = medico.getId();
        int idPaciente = paciente.getId();

        String sql = "insert into atendimento(paciente_id, medico_id, data_atendimento) values(?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idMedico);
            stmt.setString(3, dataDeAtendimento);

            stmt.executeUpdate();
            stmt.close();

            System.out.println("O atendimento do Médico "+medico.getNome()+" para o paciente "+paciente.getNome()+" foi adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao tentar adicionar um atendimento medico");
            e.printStackTrace();
        }
    }

    public ArrayList<Atendimento> listarTodosAtendimentos(Connection conexao) {
        String sql = "select id, medico_id, paciente_id, data_atendimento from atendimento";
        ArrayList<Atendimento> atendimentos = new ArrayList<>();

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            Atendimento a;
            int id;
            int medico_id;
            int paciente_id;
            String data;

            while(rs.next()) {
                id = rs.getInt("id");
                medico_id = rs.getInt("medico_id");
                paciente_id = rs.getInt("paciente_id");
                data = rs.getString("data_atendimento");

                a = new Atendimento();
                a.setId(id);
                a.setMedico_id(medico_id);
                a.setPaciente_id(paciente_id);
                a.setData(data);

                atendimentos.add(a);
            }

            rs.close();
            stmt.close();

            System.out.println("Todos os atendimentos foram lidos com sucesso!");
        } catch (SQLException e) {
            System.out.println("Não foi possível pesquisar todos os dados dos atendimentos");
            e.printStackTrace();
        }

        return atendimentos;
    }

    public Atendimento pesquisarAtendimentoPorId(int id, Connection conexao) {
        String sql = "select id, medico_id, paciente_id, data_atendimento from atendimento where id = ?";
        Atendimento a = new Atendimento();

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();

            int id1, medico_id, paciente_id;
            String data;

            while(rs.next()) {
                id1 = rs.getInt("id");
                medico_id = rs.getInt("medico_id");
                paciente_id = rs.getInt("paciente_id");
                data = rs.getString("data_atendimento");

                a.setId(id1);
                a.setMedico_id(medico_id);
                a.setPaciente_id(paciente_id);
                a.setData(data);
            }

            rs.close();
            stmt.close();

            System.out.println("Atendimento encontrado com Sucesso!");
        } catch(SQLException e) {
            System.out.println("Erro ao tentra pesquisar o atendimento pelo ID!");
            e.printStackTrace();
        }

        return a;
    }

    public void atualizarAtendimento(Atendimento atendimento, Connection conexao) {
        String sql = "update atendimento set medico_id = ?, paciente_id = ? where id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, atendimento.getMedico_id());
            stmt.setInt(2, atendimento.getPaciente_id());
            stmt.setInt(3, atendimento.getId());

            stmt.executeUpdate();
            stmt.close();

            System.out.println("O atendimento foi atualizado com Sucesso");
            System.out.println(atendimento.toString());
        } catch (SQLException e) {
            System.out.println("Erro ao tentar atualizar os dados do atendimento");
            e.printStackTrace();
        }
    }

    public void deletarAtendimentoPorID(int id, Connection conexao) {
        String sql = "delete from atendimento where id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,id);
            stmt.executeUpdate();

            stmt.close();

            System.out.println("O atendimento de ID: "+id+" foi deletado com sucesso!");
        } catch(SQLException e) {
            System.out.println("Erro ao tentar deletar o atendimento por id!");
            e.printStackTrace();
        }
    }

    public void executarScript(Connection conexao) {
        try {
            String script = new String(Files.readAllBytes(Paths.get("banco.sql")));
            System.out.println("O Arquivo foi lido com Sucesso!");
            try {
                Statement stmt = conexao.createStatement();
                String[] comandos = script.split(";");

                for (String comando: comandos) {
                    comando = comando.trim();

                    if (!comando.isEmpty()) {
                        stmt.execute(comando);
                    }
                }
                stmt.close();
                System.out.println("O script banco.sql foi executado corretamente!");
            } catch (SQLException sqlException) {
                System.out.println("Não foi possível executar o script!");
                sqlException.printStackTrace();
            }
        } catch(IOException e) {
            System.out.println("Erro ao ler o arquivo!");
            e.printStackTrace();
        }
    }

    public void executarBackup(Connection conexao) {
        try {
            String script = new String(Files.readAllBytes(Paths.get("backup.sql")));
            System.out.println("O Arquivo de backup.sql foi lido com Sucesso!");
            try {
                Statement stmt = conexao.createStatement();
                String[] comandos = script.split(";");

                for (String comando: comandos) {
                    comando = comando.trim();

                    if (!comando.isEmpty()) {
                        stmt.execute(comando);
                    }
                }
                stmt.close();
                System.out.println("O backup.sql foi executado corretamente!");
            } catch (SQLException sqlException) {
                System.out.println("Não foi possível executar o backup.sql!");
                sqlException.printStackTrace();
            }
        } catch(IOException e) {
            System.out.println("Erro ao ler o arquivo backup.sql!");
            e.printStackTrace();
        }
    }

    public void backup(Connection conexao) {
        ArrayList<Paciente> pacientes = pesquisarTodosPacientes(conexao);
        ArrayList<Medico> medicos = pesquisarTodosMedicos(conexao);
        ArrayList<Atendimento> atendimentos = listarTodosAtendimentos(conexao);
    }

}
