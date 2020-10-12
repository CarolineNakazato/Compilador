/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;

/**
 *
 * @author Dell
 */
class TabelaSimbolo {
    private static TabelaSimbolo instance = null;
    private ArrayList<Simbolo> simbolos;

    public static TabelaSimbolo getInstance() {
        if (instance == null) {
            instance = new TabelaSimbolo();
        }
        return instance;
    }

    public TabelaSimbolo() {
        simbolos = new ArrayList<>();
    }

    public synchronized void inserirSimbolo(Simbolo novo) {
        simbolos.add(novo);
    }

    public synchronized Simbolo getSymbolData(String lexema) {
        for (Simbolo aux : this.simbolos) {
            if (aux.getLexema().equals(lexema)) {
                return aux;
            }
        }

        return null;
    }

    public synchronized void setTipoSimbolo(String tipo) {
        for (int i = simbolos.size() - 1; i >= 0; i--) {
            if (this.simbolos.get(i) instanceof Variavel) {
                if (((Variavel) this.simbolos.get(i)).getTipo() == null) {
                    ((Variavel) this.simbolos.get(i)).setTipo(tipo);
                }
            } else if (this.simbolos.get(i) instanceof Funcao) {
                if (((Funcao) this.simbolos.get(i)).getTipo() == null) {
                    ((Funcao) this.simbolos.get(i)).setTipo(tipo);
                }
            }
        }
    }

    public synchronized void setSimbolo(ArrayList<Simbolo> simbolo) {
        this.simbolos = simbolo;
    }

   /* public synchronized ArrayList<Simbolo> requestSymbols() {
        return this.simbolos;
    }*/

    public synchronized void printTabela() {
        for (Simbolo aux : this.simbolos) {
            if (aux instanceof Variavel) {
                ((Variavel) aux).printVariavel();
            } else if (aux instanceof ProcedureProgram) {
                ((ProcedureProgram) aux).printProcedureProgram();
            } else if (aux instanceof Funcao) {
                ((Funcao) aux).printFuncao();
            }
        }
    }
    
}
