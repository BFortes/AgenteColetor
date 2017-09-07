import java.util.List;
import java.util.Scanner;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Main {

  public static void main(String[] args) {
      

    int tamanho = 12;
    int lixeiras = 3;
    int recargas = 3;
    
    /*
    Scanner scanner = new Scanner(System.in);  

    System.out.print("Digite o tamanho da matriz: ");
    tamanho = scanner.nextInt();

    System.out.print("Digite a quantidade de lixeiras no ambiente: ");
    lixeiras = scanner.nextInt();

    System.out.print("Digite a quantidade de pontos de recarga no ambiente: ");
    recargas = scanner.nextInt();

    Ambiente ambiente = new Ambiente(tamanho, new int[]{0, 0}, lixeiras, recarga);
    */    

    int[] posicaoInicialAgente = new int[2];
    posicaoInicialAgente[0] = 0;//linha
    posicaoInicialAgente[1] = 0;//coluna

    Ambiente ambiente = new Ambiente(tamanho, lixeiras, recargas);

    Agente agente = new Agente(posicaoInicialAgente, 10, 100);
    List<String> movimeltons = agente.Random_path(ambiente);
    //int[] movieltons = agente.A_Star_path();

    int i = 0;
    for(String m : movimeltons) {

      System.out.println(m + (i < tamanho ? "," : ""));

      i++;
    }
  }
}
