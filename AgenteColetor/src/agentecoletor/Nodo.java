/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentecoletor;

/**
 *
 * @author Marlon Fontoura
 */
public class Nodo {
    
    private String tipo = "[ ]";
    
    public Nodo (EstadosNodo estado){
    
        switch (estado){
            case celulaVazia: tipo = "[ ]"; break;
            case celulaSuja:  tipo = "[s]"; break; //ajustar pra 's' depois, está assim pra debug só
            case parede:      tipo = "[X]"; break;
            case lixeira:     tipo = "[L]"; break;
            case recarga:     tipo = "[R]"; break;
            case agente:      tipo = "[A]"; break;
        }
    }
        
     public void setTipo(EstadosNodo estado){
         switch (estado){
            case celulaVazia: tipo = "[ ]"; break;
            case celulaSuja:  tipo = "[s]"; break;//ajustar pra 's' depois, está assim pra debug só
            case parede:      tipo = "[X]"; break;
            case lixeira:     tipo = "[L]"; break;
            case recarga:     tipo = "[R]"; break;
            case agente:      tipo = "[A]"; break;
        }
     }
     
    public void setTipo(String estado){
        tipo = estado;
    } 
     
    public String getTipo(){
        return tipo;
    } 
     
    public boolean estaVazio(){
        return tipo.equals("[ ]");              
    }
    
}
