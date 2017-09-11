########### AGENTE COLETOR ###########

Bruno Paz e Marlon Fontoura

TODO: - caminhamento semi aleatório para qualquer posição inicial do agente(funciona posicionando o agente nos extremos).
      - geração correta do ambiente para qualquer tamanho n

Ambiente: composto por nodos.

  nodos são representados por:
  
    [ ] -> nodo vazio
    ['] -> nodo visitado
    [X] -> nodo parede
    [s] -> nodo sujo
    [L] -> nodo lixeira
    [R] -> nodo recarga
    [A] -> nodo agente
    
    exemplo:
    
    [A][ ][s][s][ ][s][ ][s][ ][ ][ ][ ]
    [ ][ ][ ][ ][ ][ ][s][ ][ ][ ][ ][s]
    [s][ ][X][X][ ][s][ ][s][X][X][ ][ ]
    [s][ ][L][X][ ][ ][ ][ ][X][s][L][s]
    [s][ ][ ][X][ ][ ][ ][ ][X][s][s][ ]
    [s][s][R][X][ ][s][ ][ ][X][ ][s][s]
    [ ][ ][ ][X][ ][ ][ ][s][X][ ][R][ ]
    [ ][s][ ][X][ ][ ][s][ ][X][ ][ ][ ]
    [ ][ ][ ][X][ ][s][ ][ ][X][L][ ][ ]
    [ ][ ][X][X][ ][ ][s][s][X][X][ ][ ]
    [s][ ][ ][ ][ ][s][ ][s][ ][ ][ ][ ]
    [ ][ ][s][s][s][s][ ][ ][s][s][ ][ ]