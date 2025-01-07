import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudJavaMySQL {

    // Configurações do banco
    private static final String URL = "jdbc:mysql://localhost:3306/crud_java?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; // Seu usuário do MySQL
    private static final String PASSWORD = "maximus2011@MF"; // Sua senha do MySQL

    public static void main(String[] args) {
        // Testando as operações do CRUD
        try {
            inserirUsuario("Naruto Uzumaki", "naruto@konoha.com");
            listarUsuarios();
            atualizarUsuario(1, "Sasuke Uchiha", "sasuke@konoha.com");
            listarUsuarios();
            deletarUsuario(1);
            listarUsuarios();
        } catch (SQLException e) {
            System.out.println("Erro no CRUD: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para conectar ao banco
    private static Connection conectar() throws SQLException {
        // Retorna uma conexão com o banco de dados
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE - Inserir usuário
    private static void inserirUsuario(String nome, String email) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso! Linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
            throw e;
        }
    }

    // READ - Listar usuários
    private static void listarUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuario";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- Lista de Usuários ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            throw e;
        }
    }

    // UPDATE - Atualizar usuário
    private static void atualizarUsuario(int id, String nome, String email) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso! Linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            throw e;
        }
    }

    // DELETE - Deletar usuário
    private static void deletarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso! Linhas afetadas: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
            throw e;
        }
    }
}
