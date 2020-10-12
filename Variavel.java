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
class Variavel extends Simbolo{
    private String tipo;
    private String memoria;

    public Variavel() {
        super();
        tipo = null;
        memoria = null;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMemoria(String memoria) {
        this.memoria = memoria;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getMemoria() {
        return this.memoria;
    }

    public void printVariavel() {
        super.printSimbolo("Variavel");
        System.out.printf("[Variavel] | Tipo: %s\n", getTipo());
    }
   
}
