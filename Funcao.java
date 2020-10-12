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
public class Funcao extends Simbolo {

    private String tipo;

    public Funcao() {
        super();
        this.tipo = null;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void printFuncao() {
        super.printSimbolo("Funcao");
        System.out.printf("[Funcao] | Tipo: %s\n", getTipo());
    }
}
