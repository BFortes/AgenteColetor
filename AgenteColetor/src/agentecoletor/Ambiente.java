package agentecoletor;

import java.util.Random;

/**
 *
 * @author 10100335
 */

public class Ambiente {

    private String[][] m_matriz;
    
    public Ambiente(int tamanho, int[] posAgente, int qtdLixeiras, int qtdRecargas) {
    
        int qtdLixoPor = ((int) (Math.random() * (85 - 40))) + 40;
        int totalCelulasLixo = (int)(tamanho * (qtdLixoPor*0.1));
        
        m_matriz = new String[tamanho][tamanho];
        
        for(int i = 0; i < tamanho; i++) {
            
            for(int j = 0; j < tamanho; j++) {
                
                if(j == 2 && i == 2 
                    || j == 2 && i == tamanho-3
                        || j == 3 && (i >= 2 && i <= tamanho-3))
                    m_matriz[i][j] = "[X]";
                else if(j == tamanho-4 && i == 3 
                        || j == tamanho-3 && i == 2
                            || j == tamanho-3 && i == tamanho-3
                                || j == tamanho-4 && (i >= 2 && i <= tamanho-3))
                    m_matriz[i][j] = "[X]";
                else if((Math.random() * 1) > 0.5)
                    m_matriz[i][j] = "[s]";
                else
                    m_matriz[i][j] = "[ ]";
                
                System.out.print(m_matriz[i][j]);
            }
            
            System.out.println();
        }
    }
    
}
