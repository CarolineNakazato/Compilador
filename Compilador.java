/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 *
 * @author Dell
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        CompiladorFrame frame = new CompiladorFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        /**Scanner ler = new Scanner(System.in);
        System.out.println("Informe o caminho e o nome do arquivo:");
        String filePath = ler.nextLine();
        
        AnalisadorLexico lexico = AnalisadorLexico.getInstance();
        lexico.analisadorLexicoNivel1(filePath);**/
    }
    
}

