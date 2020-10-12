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
public class Simbolo {
    private String lexema;
    private String escopo;

    public Simbolo() {
        lexema = null;
        escopo = null;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setEscopo(String escopo) {
        this.escopo = escopo;
    }

    public String getLexema() {
        return this.lexema;
    }

    public String getEscopo() {
        return this.escopo;
    }

    public void printSimbolo(String tipo) {
        System.out.printf("[" + tipo + "] | Lexema: %s\n", getLexema());
        System.out.printf("[" + tipo + "] | escopo: %s\n", getEscopo());
    }
}
