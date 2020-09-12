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
        Scanner ler = new Scanner(System.in);
        System.out.printf("Informe o caminho e o nome do arquivo:\n");
        
        String nome = ler.nextLine();
        AnalisadorLexico al = new AnalisadorLexico();
        System.out.printf(al.AnalisadorLexicoNivel1(nome).toString());
    }
    
}

