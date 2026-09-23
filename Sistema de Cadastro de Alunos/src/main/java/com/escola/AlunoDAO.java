package com.escola;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public void cadastrar(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, matricula, data_nascimento, curso) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setObject(3, aluno.getDataNascimento());
            stmt.setString(4, aluno.getCurso());

            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso no banco de dados!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar aluno: " + e.getMessage(), e);
        }
    }

    public List<Aluno> listar() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setMatricula(rs.getString("matricula"));
                
                Date dataNasc = rs.getDate("data_nascimento");
                if (dataNasc != null) {
                    aluno.setDataNascimento(dataNasc.toLocalDate());
                }
                
                aluno.setCurso(rs.getString("curso"));
                alunos.add(aluno);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage(), e);
        }

        return alunos;
    }
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, matricula = ?, data_nascimento = ?, curso = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setObject(3, aluno.getDataNascimento());
            stmt.setString(4, aluno.getCurso());
            stmt.setInt(5, aluno.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Aluno deletado com sucesso!");
            } else {
                System.out.println("Nenhum aluno encontrado com o ID informado.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno: " + e.getMessage(), e);
        }
    }
}