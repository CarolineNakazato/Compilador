/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Dell
 */
public class Token {
    private String simbolo;
    private String lexema;
    private String linha;

    public Token() {
        simbolo = null;
        lexema = null;
        linha = null;
    }


    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getSimbolo() {
        return this.simbolo;
    }

    public String getLexema() {
        return this.lexema;
    }
    
    public String getLinha() {
        return this.linha;
    }
    
    public void setLinha(String linha) {
        this.linha = linha;
    }

    public void print() {
        System.out.printf("Símbolo: %s\n", simbolo);
        System.out.printf("Lexema: %s\n", lexema);
        System.out.printf("Encontrado na Linha: %s\n", linha);
        System.out.printf("--------------------------\n");
    }
}
