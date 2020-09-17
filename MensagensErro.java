/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author nicku
 */
public class MensagensErro {
    
    private static MensagensErro instance = null;
    private String messageError;

    public static MensagensErro getInstance() {
        if (instance == null) {
            instance = new MensagensErro();
        }
        return instance;
    }
}
