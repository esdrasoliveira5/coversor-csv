package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe Conversor.
 */
public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados na pasta de
   * saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato requerido pelo
   *        subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou gravar os
   *         arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    // TODO: Implementar.
    if (!pastaDeSaidas.exists()) {
      pastaDeSaidas.mkdirs();
    }
    for (File f : pastaDeEntradas.listFiles()) {
      this.lerArquivo(f, pastaDeSaidas);
    }
  }

  /**
   * metodo Criar Arquivo.
   */

  public void criarArquivo(File newArquivo) {
    try {
      if (newArquivo.createNewFile()) {
        System.out.println("File created: " + newArquivo.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  /**
   * Metodo lerArquivo.
   */
  public void lerArquivo(File entradaArquivo, File pastaDeSaidas) {
    try {
      FileReader leitorArquivo = new FileReader(entradaArquivo);
      BufferedReader bufferedLeitor = new BufferedReader(leitorArquivo);

      File newArquivo = new File(pastaDeSaidas + File.separator + entradaArquivo.getName());
      this.criarArquivo(newArquivo);

      String conteudoLinha = bufferedLeitor.readLine();

      FileWriter escritorArquivo = null;
      BufferedWriter bufferedEscritor = null;
      escritorArquivo = new FileWriter(newArquivo);
      bufferedEscritor = new BufferedWriter(escritorArquivo);

      while (conteudoLinha != null) {
        if (conteudoLinha.equals("Nome completo,Data de nascimento,Email,CPF")) {
          bufferedEscritor.write(conteudoLinha);
          bufferedEscritor.newLine();

          conteudoLinha = bufferedLeitor.readLine();
        } else {
          String newLinha = this.editarlinha(conteudoLinha);
          bufferedEscritor.write(newLinha);
          bufferedEscritor.newLine();

          conteudoLinha = bufferedLeitor.readLine();
        }
      }

      bufferedEscritor.flush();
      bufferedLeitor.close();
      bufferedEscritor.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * metodo Editar a linha.
   */
  public String editarlinha(String conteudoLinha) {
    String[] newLinha = conteudoLinha.split(",");
    String newName = newLinha[0].toUpperCase();

    String[] arrayData = newLinha[1].split("/");
    String newData = "";
    for (int i = arrayData.length - 1; i >= 0; i -= 1) {
      if (i == 2) {
        newData = newData + arrayData[i];

      } else {
        newData = newData + "-" + arrayData[i];
      }
    }

    String email = newLinha[2];

    String[] arrayCpf = newLinha[3].split("");
    String newCpf = "";
    for (int i = 0; i < arrayCpf.length; i += 1) {
      if (i == 3 || i == 6) {
        newCpf = newCpf + "." + arrayCpf[i];
      } else if (i == 9) {
        newCpf = newCpf + "-" + arrayCpf[i];
      } else {
        newCpf = newCpf + arrayCpf[i];
      }
    }
    return newName + "," + newData + "," + email + "," + newCpf;
  }


  /**
   * Escreve no Arquivo.
   */
  public void escreverArquivo(File newArquivo, String dados) {
    FileWriter escritorArquivo = null;
    BufferedWriter bufferedEscritor = null;
    try {
      escritorArquivo = new FileWriter(newArquivo);
      bufferedEscritor = new BufferedWriter(escritorArquivo);
      bufferedEscritor.write(dados);
      bufferedEscritor.newLine();
      bufferedEscritor.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
