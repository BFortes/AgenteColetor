package agentecoletor;

import java.util.Random;

/**
 *
 * @author BrunoPaz e MarlonFontoura
 */

public class Ambiente {

    private String[][] m_matriz;
    private int tamanho, qtdLixeiras, qtdRecargas;
    
    private String celulaVazia = "[ ]";
    private String celulaSuja  = "[ ]"; //ajustar pra 's' depois, está assim pra debug só
    private String parede      = "[X]";
    private String lixeira     = "[L]";
    private String recarga     = "[R]";

    public Ambiente(int tamanho, int[] posAgente, int qtdLixeiras, int qtdRecargas) {
        
        this.tamanho = tamanho;
        this.qtdLixeiras = qtdLixeiras;
        this.qtdRecargas = qtdRecargas;
        
        System.out.print("Total de Células: " + tamanho * tamanho + "\n");
        
        double qtdLixoPorcentagem = ((int) (Math.random() * (85 - 40))) + 40;
        System.out.print("Porcentagem de lixo: " + qtdLixoPorcentagem + "\n");
        
        int totalCelulasLixo = (int)( (tamanho*tamanho) * (qtdLixoPorcentagem/100));
        System.out.print("Células com Lixo: " + totalCelulasLixo + "\n");
        
        m_matriz = new String[tamanho][tamanho];
        
        for(int i = 0; i < tamanho; i++) { //i: linha, j: coluna
            
            for(int j = 0; j < tamanho; j++) {
                
                //desenhar parede da esquerda
                if(i == 2 && j == 2
                    || i == tamanho-3 && j == 2
                        || (i >= 2 && i <= tamanho-3) && j == 3)
                    m_matriz[i][j] = parede;
                
                //desenhar parede da direita
                else if(i == 3 && j == tamanho-4
                        || i == 2 && j == tamanho-3 
                            || i == tamanho-3 && j == tamanho-3
                                || (i >= 2 && i <= tamanho-3) && j == tamanho-4)
                    m_matriz[i][j] = parede;
                
                //colocar lixos
                else if((Math.random() * 1) > 0.5 && totalCelulasLixo > 0){
                    m_matriz[i][j] = celulaSuja; //escolher o caracter ideal
                    totalCelulasLixo--;
                }
                
                //célular vazias
                else
                    m_matriz[i][j] = celulaVazia;
            }    
        }
        colocarLixeiras();
        colocarRecargas();
        desenhaAmbiente();
    }
    
    public void desenhaAmbiente(){
        for(int i = 0; i < tamanho; i++){
            System.out.print("\n");
            for(int j = 0; j < tamanho; j++) {
                System.out.print(m_matriz[i][j]);
            }
        }
        System.out.print("\n");
    }
    
    public void colocarLixeiras(){
        System.out.print("Número de lixeiras: " + qtdLixeiras + "\n");
        while (qtdLixeiras > 0){
            for(int i = 0; i < tamanho; i++){
                for(int j = 0; j < tamanho; j++) {
                    if ((i > 2 && i <tamanho-3)//dentro das linhas das paredes 
                            && (j < 3 || j > tamanho-4)){//dentro das colunas das paredes
                        if((Math.random() * 1) > 0.7 && m_matriz[i][j].equals(celulaVazia) && qtdLixeiras>0){  //0,7 pra ficar mais espalhado
                             m_matriz[i][j] = lixeira;
                            qtdLixeiras--;
                        }
                    }
                }
            }
        }
    }
    
    public void colocarRecargas(){
        System.out.print("Número de recargas: " + qtdRecargas + "\n");
        while (qtdRecargas > 0){
            for(int i = 0; i < tamanho; i++){
                for(int j = 0; j < tamanho; j++) {
                    if ((i > 2 && i <tamanho-3)//dentro das linhas das paredes 
                            && (j < 3 || j > tamanho-4)){//dentro das colunas das paredes
                        if((Math.random() * 1) > 0.7 && m_matriz[i][j].equals(celulaVazia) && qtdRecargas>0){ //0,7 pra ficar mais espalhado
                             m_matriz[i][j] = recarga;
                            qtdRecargas--;
                        }
                    }
                }
            }
        }
    }
    
}
