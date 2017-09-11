/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Main {

  public static void main(String[] args) throws CloneNotSupportedException{
      

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

    Point posicaoInicialAgente = new Point(0,0);

    Ambiente ambiente = new Ambiente(tamanho, lixeiras, recargas);

    Agente agente = new Agente(posicaoInicialAgente, 10, 100);
    agente.Random_path(ambiente);
  }
}
