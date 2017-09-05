package agentecoletor;

import java.util.Scanner;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Main {

  public static void main(String[] args) {
      
    /*
      Scanner scanner = new Scanner(System.in);  

      System.out.print("Digite o tamanho da matriz: ");
      int tamanho = scanner.nextInt();

      System.out.print("Digite a quantidade de lixeiras no ambiente: ");
      int lixeiras = scanner.nextInt();

      System.out.print("Digite a quantidade de pontos de recarga no ambiente: ");
      int recarga = scanner.nextInt();

      Ambiente ambiente = new Ambiente(tamanho, new int[]{0, 0}, lixeiras, recarga);
    */
    
     int[] posicaoInicialAgente = new int[2];
     posicaoInicialAgente[0] = 0;//linha
     posicaoInicialAgente[1] = 0;//coluna

     Agente agente = new Agente(posicaoInicialAgente, 100, 100);

     Ambiente ambiente = new Ambiente(agente, 12, 5, 7);
  }
}
