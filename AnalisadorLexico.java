/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class AnalisadorLexico {
    private ArrayList<Character> ignora;
    private ArrayList<Character> aritmedico;
    private ArrayList<Character> relacional;
    private ArrayList<Character> pontuacao;
    private char currentChar;
    private BufferedReader br;
    private int indexFileLine;
    private int charRead;
    private String error;
    
    public AnalisadorLexico() {
        br = null;
        indexFileLine = 1;

        ignora = new ArrayList<>();
        aritmedico = new ArrayList<>();
        relacional = new ArrayList<>();
        pontuacao = new ArrayList<>();

        ignora.add(' ');
        ignora.add('{');
        ignora.add('\n');
        ignora.add('\r');
        ignora.add('\t');

        aritmedico.add('+');
        aritmedico.add('-');
        aritmedico.add('*');
        //aritmedico.add('/');
        
        relacional.add('>');
        relacional.add('<');
        relacional.add('=');
        relacional.add('!');

        pontuacao.add(';');
        pontuacao.add(',');
        pontuacao.add('(');
        pontuacao.add(')');
        pontuacao.add('.');
    }

     private boolean openFile(String codePath) {
        try {
            br = new BufferedReader(new FileReader(codePath));
            //System.out.println("[OpenFile] | File opened");
            return true;

        } catch (FileNotFoundException exception) {
            System.out.println("Erro arquivo não encontrado!");
        }

        return false;
    }

    private boolean closeFile() {
        try {
            if (br != null) {
                br.close();
                //System.out.println("[CloseFile] | File closed");
            }

            return true;
        } catch (IOException exception) {
            System.out.println("Erro arquivo não encontrado!");
        }

        return false;
    }

    //TA CERTO ISSO AQUI?????? if ==???????
    protected boolean hasFileEnd() {
        if (charRead == -1) {
            return true;
        } else {
            return false;
        }
    }
    
    public Token AnalisadorLexicoNivel1(String nome) throws FileNotFoundException, IOException {
        if (br == null) {
            if (!this.openFile(nome)) {
                return null;
            } else {
                charRead = br.read();
                currentChar = (char) charRead;
            }
        }

        try {
            if (!hasFileEnd()) {
                while ((ignora.contains(currentChar)) && charRead != -1) {
                    if (currentChar == '{') {

                        while (currentChar != '}' && charRead != -1) {
                            charRead = br.read();
                            currentChar = (char) charRead;
                        }

                        charRead = br.read();
                        currentChar = (char) charRead;

                    } else if (currentChar == '\n') {
                        indexFileLine++;
                        charRead = br.read();
                        currentChar = (char) charRead;

                    } else {
                        charRead = br.read();
                        currentChar = (char) charRead;

                        while (currentChar == ' ' && charRead != -1) {
                            charRead = br.read();
                            currentChar = (char) charRead;
                        }
                    }
                }

                if (!hasFileEnd()) {
                    return this.pegaToken(indexFileLine);

                } else {
                    this.closeFile();
                }

            } else {
                this.closeFile();
            }

        } catch (Exception e) {
            System.out.printf("Erro: "+e.toString()+"\n");
            /*if (e.getMessage() != null) {
                setErrorMessage(e.getMessage());
            } else {
                setErrorMessage("[ Lexical Error ] | Something unexpected happened");
            }*/
        }
        return null;
    }
    
    public Token pegaToken(int indexFile) throws IOException{
        Token newToken = new Token();

        if (Character.isDigit(currentChar)) {
            newToken = this.ehDigito(currentChar, indexFile);

        } else if (Character.isLetter(currentChar)) {
            newToken = this.ehLetra(currentChar, indexFile);

        } else if (currentChar == ':') {
            newToken = this.EhAtribuicao(currentChar, indexFile);

        } else if (aritmedico.contains(currentChar)) {
            newToken = this.EhAritmedico(currentChar, indexFile);

        } else if (relacional.contains(currentChar)) {
            newToken = this.ehRelacional(currentChar, indexFile);

        } else if (pontuacao.contains(currentChar)) {
            newToken = this.ehPontucao(currentChar, indexFile);

        } else {
            System.out.printf("Erro: caracter inválido!\n");
        }

        return newToken;
    }

    private Token ehDigito(char character, int lineIndex) throws IOException {
        Token digito = new Token();
        String numero = "";

        numero += character;
        charRead = br.read();
        currentChar = (char) charRead;

        while (Character.isDigit(currentChar)) {
            numero += currentChar;
            charRead = br.read();
            currentChar = (char) charRead;
        }

        digito.setSimbolo("snumero");
        digito.setLexema(numero);
        return digito;
    }

    private Token ehLetra(char character, int lineIndex) throws IOException {
        Token letra = new Token();
        String palavra = "";

        palavra += character;
        charRead = br.read();
        currentChar = (char) charRead;

        while (Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_') {
            palavra += currentChar;
            charRead = br.read();
            currentChar = (char) charRead;
        }

        //letter.setLine(Integer.toString(lineIndex));

        switch (palavra) {
            case "programa":
                letra.setSimbolo("sprograma");
                break;
            case "se":
                letra.setSimbolo("sse");
                break;
            case "entao":
                letra.setSimbolo("sentao");
                break;
            case "senao":
                letra.setSimbolo("ssenao");
                break;
            case "enquanto":
                letra.setSimbolo("senquanto");
                break;
            case "faca":
                letra.setSimbolo("sfaca");
                break;
            case "inicio":
                letra.setSimbolo("sinício");
                break;
            case "fim":
                letra.setSimbolo("sfim");
                break;
            case "escreva":
                letra.setSimbolo("sescreva");
                break;
            case "leia":
                letra.setSimbolo("sleia");
                break;
            case "var":
                letra.setSimbolo("svar");
                break;
            case "inteiro":
                letra.setSimbolo("sinteiro");
                break;
            case "booleano":
                letra.setSimbolo("sbooleano");
                break;
            case "verdadeiro":
                letra.setSimbolo("sverdadeiro");
                break;
            case "falso":
                letra.setSimbolo("sfalso");
                break;
            case "procedimento":
                letra.setSimbolo("sprocedimento");
                break;
            case "funcao":
                letra.setSimbolo("sfuncao");
                break;
            case "div":
                letra.setSimbolo("sdiv");
                break;
            case "e":
                letra.setSimbolo("se");
                break;
            case "ou":
                letra.setSimbolo("sou");
                break;
            case "nao":
                letra.setSimbolo("snao");
                break;
            default:
                letra.setSimbolo("sidentificador");
                break;
        }

        letra.setLexema(palavra);
        return letra;
    }

    private Token EhAtribuicao(char character, int lineIndex) throws IOException {
        Token attribution = new Token();
        String attr = "";

        attr += character;
        //attribution.setLine(Integer.toString(lineIndex));
        charRead = br.read();
        currentChar = (char) charRead;

        if (currentChar == '=') {
            attr += currentChar;
            attribution.setSimbolo("satribuição");
            charRead = br.read();
            currentChar = (char) charRead;

        } else {
            attribution.setSimbolo("sdoispontos");
        }

        attribution.setLexema(attr);
        return attribution;
    }

    private Token EhAritmedico(char character, int lineIndex) throws IOException {
        Token arit = new Token();

        //aritmetic.setLine(Integer.toString(lineIndex));
        switch (character) {
            case '+':
                arit.setSimbolo("smais");
                break;
            case '-':
                arit.setSimbolo("smenos");
                break;
            case '*':
                arit.setSimbolo("smult");
                break;
            /*case '/':
                arit.setSimbolo("sdivi");
                break;*/
            default:
                break;
        }

        arit.setLexema(Character.toString(character));
        charRead = br.read();
        currentChar = (char) charRead;
        return arit;
    }

    private Token ehRelacional(char character, int lineIndex) throws IOException {
         Token relational = new Token();
        String operation = "";

        operation += character;
        //relational.setLine(Integer.toString(lineIndex));
        switch (character) {
            case '>':
                charRead = br.read();
                currentChar = (char) charRead;
                if (currentChar == '=') {
                    operation += currentChar;
                    relational.setSimbolo("smaiorig");
                    charRead = br.read();
                    currentChar = (char) charRead;
                    
                } else {
                    relational.setSimbolo("smaior");
                }   break;
            case '<':
                charRead = br.read();
                currentChar = (char) charRead;
                if (currentChar == '=') {
                    operation += currentChar;
                    relational.setSimbolo("smenorig");
                    charRead = br.read();
                    currentChar = (char) charRead;
                    
                } else {
                    relational.setSimbolo("smenor");
                }   break;
            case '=':
                relational.setSimbolo("sig");
                charRead = br.read();
                currentChar = (char) charRead;
                break;
            case '!':
                charRead = br.read();
                currentChar = (char) charRead;
                if (currentChar == '=') {
                    operation += currentChar;
                    relational.setSimbolo("sdif");
                    charRead = br.read();
                    currentChar = (char) charRead;
                    
                } else {
                    System.out.printf("Erro.\n");
                    //throw new Exception(message.lexicalError(Integer.toString(lineIndex), operation));
                }   break;
            default:
                break;
        }

        relational.setLexema(operation);
        return relational;
    }

    private Token ehPontucao(char character, int lineIndex) throws IOException {
        Token punctuation = new Token();

        //punctuation.setLine(Integer.toString(lineIndex));
        switch (character) {
            case ';':
                punctuation.setSimbolo("sponto_vírgula");
                break;
            case ',':
                punctuation.setSimbolo("svirgula");
                break;
            case '(':
                punctuation.setSimbolo("sabre_parênteses");
                break;
            case ')':
                punctuation.setSimbolo("sfecha_parênteses");
                break;
            case '.':
                punctuation.setSimbolo("sponto");
                break;
            default:
                break;
        }

        punctuation.setLexema(Character.toString(character));
        charRead = br.read();
        currentChar = (char) charRead;
        return punctuation;
    }
}
