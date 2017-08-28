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
        Ambiente ambiente = new Ambiente(12, new int[]{0, 0}, 5, 7);
    }
    
}
