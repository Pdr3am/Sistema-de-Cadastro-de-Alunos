package com.escola;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoDAO alunoDAO = new AlunoDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- SISTEMA DE CADASTRO DE ALUNOS ---");
            System.out.println("1. Cadastrar novo aluno");
            System.out.println("2. Listar todos os alunos");
            System.out.println("3. Atualizar dados de um aluno");
            System.out.println("4. Deletar um aluno");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Opção inválida! Digite um número.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome do aluno: ");
                    String nome = scanner.nextLine();

                    System.out.print("Matrícula: ");
                    String matricula = scanner.nextLine();

                    System.out.print("Data de nascimento (dd/mm/aaaa): ");
                    String dataStr = scanner.nextLine();
                    LocalDate dataNascimento = LocalDate.parse(dataStr, formatter);

                    System.out.print("Curso: ");
                    String curso = scanner.nextLine();

                    Aluno novoAluno = new Aluno(nome, matricula, dataNascimento, curso);
                    alunoDAO.cadastrar(novoAluno);
                    break;

                case 2:
                    List<Aluno> alunos = alunoDAO.listar();
                    System.out.println("\n--- LISTA DE ALUNOS CADASTRADOS ---");
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno registrado.");
                    } else {
                        for (Aluno a : alunos) {
                            String dataFormatada = a.getDataNascimento() != null ? a.getDataNascimento().format(formatter) : "Não informada";
                            System.out.println("ID: " + a.getId() + 
                                               " | Nome: " + a.getNome() + 
                                               " | Matrícula: " + a.getMatricula() + 
                                               " | Nasc: " + dataFormatada + 
                                               " | Curso: " + a.getCurso());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID do aluno que deseja atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    System.out.print("Nova matrícula: ");
                    String novaMatricula = scanner.nextLine();

                    System.out.print("Nova data de nascimento (dd/mm/aaaa): ");
                    String novaDataStr = scanner.nextLine();
                    LocalDate novaDataNascimento = LocalDate.parse(novaDataStr, formatter);

                    System.out.print("Novo curso: ");
                    String novoCurso = scanner.nextLine();

                    Aluno alunoAtualizado = new Aluno(novoNome, novaMatricula, novaDataNascimento, novoCurso);
                    alunoAtualizado.setId(idAtualizar);
                    alunoDAO.atualizar(alunoAtualizado);
                    break;

                case 4:
                    System.out.print("Digite o ID do aluno que deseja deletar: ");
                    int idDeletar = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer

                    alunoDAO.deletar(idDeletar);
                    break;

                case 0:
                    System.out.println("Encerrando o sistema.");
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }
}