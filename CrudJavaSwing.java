import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import  com.formdev.flatlaf.FlatDarkLaf;


public class CrudJavaSwing extends JFrame {
    private JTextField nomeField, emailField, idField;
    private JTextArea outputArea;

    // Configurações do banco
    private static final String URL = "jdbc:mysql://localhost:3306/crud_java?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "maximus2011@MF";

    public CrudJavaSwing() {
        // Configuração da janela
//        setTitle("CRUD Java com Swing");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior para entradas
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("ID (para atualizar/deletar):"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);
        add(inputPanel, BorderLayout.NORTH);

        // Área de saída
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Botões para operações
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton inserirButton = new JButton("Inserir");
        JButton listarButton = new JButton("Listar");
        JButton atualizarButton = new JButton("Atualizar");
        JButton deletarButton = new JButton("Deletar");
        buttonPanel.add(inserirButton);
        buttonPanel.add(listarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(deletarButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        inserirButton.addActionListener(e -> inserirUsuario());
        listarButton.addActionListener(e -> listarUsuarios());
        atualizarButton.addActionListener(e -> atualizarUsuario());
        deletarButton.addActionListener(e -> deletarUsuario());

        setVisible(true);
    }

    // Métodos CRUD
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void inserirUsuario() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Email são obrigatórios" );
            return;
    }   else {
        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            int linhasAfetadas = stmt.executeUpdate();
            outputArea.append("Usuário inserido com sucesso! Linhas afetadas: " + linhasAfetadas + "\n");
        } catch (SQLException e) {
            outputArea.append("Erro ao inserir usuário: " + e.getMessage() + "\n");
        }
    }
    }

    private void listarUsuarios() {

        String sql = "SELECT * FROM usuario";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            outputArea.setText("--- Lista de Usuários ---\n");
            while (rs.next()) {
                outputArea.append("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome") + ", Email: " + rs.getString("email") + "\n");
            }
        } catch (SQLException e) {
            outputArea.append("Erro ao listar usuários: " + e.getMessage() + "\n");
        }
    }

    private void atualizarUsuario() {
        int id = Integer.parseInt(idField.getText());
        String nome = nomeField.getText();
        String email = emailField.getText();
        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Email são obrigatórios!");
            return;
    }   else {


        String sql = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            int linhasAfetadas = stmt.executeUpdate();
            outputArea.append("Usuário atualizado com sucesso! Linhas afetadas: " + linhasAfetadas + "\n");
        } catch (SQLException e) {
            outputArea.append("Erro ao atualizar usuário: " + e.getMessage() + "\n");
        }
    }
    }
    private void deletarUsuario() {
        int id = Integer.parseInt(idField.getText());
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            outputArea.append("Usuário deletado com sucesso! Linhas afetadas: " + linhasAfetadas + "\n");
        } catch (SQLException e) {
            outputArea.append("Erro ao deletar usuário: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();

        } catch (Exception e) {
            System.out.println("Nao foi possivel aplicar o tema escuro");
        }

        SwingUtilities.invokeLater(() -> new CrudJavaSwing());
    }
}
