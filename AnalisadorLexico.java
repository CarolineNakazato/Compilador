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
    private static AnalisadorLexico instance = null;
    private final MensagensErro message = MensagensErro.getInstance();
    private ArrayList<Character> ignora;
    private ArrayList<Character> aritmetico;
    private ArrayList<Character> relacional;
    private ArrayList<Character> pontuacao;
    
    private char currentChar;
    private BufferedReader bufferedReader;
    private int indexFileLine;
    private int charRead;
    private String erro;
    
    public static AnalisadorLexico getInstance() {
        if (instance == null) {
            instance = new AnalisadorLexico();
        }
        return instance;
    }    
    
    public AnalisadorLexico() {
        bufferedReader = null;
        indexFileLine = 1;

        ignora = new ArrayList<>();
        aritmetico = new ArrayList<>();
        relacional = new ArrayList<>();
        pontuacao = new ArrayList<>();

        ignora.add(' ');
        ignora.add('{');
        ignora.add('\n');
        ignora.add('\r');
        ignora.add('\t');
        ignora.add('/');
        
        aritmetico.add('+');
        aritmetico.add('-');
        aritmetico.add('*');
        //aritmetico.add('div');
        
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
            bufferedReader = new BufferedReader(new FileReader(codePath));
            System.out.println("\n-Arquivo aberto-");
            return true;

        } catch (FileNotFoundException exception) {
            System.out.println("\nERRO: Arquivo não encontrado!\n");
        }
        return false;
    }

    private boolean closeFile() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
                System.out.println("\n-Arquivo Fechado-");
            }

            return true;
        } catch (IOException exception) {
            System.out.println("\nERRO: Arquivo não encontrado!\n");
        }
        return false;
    }

    protected boolean hasFileEnd() {
        if (charRead == -1) {
            return true;
        } else {
            return false;
        }
    }
    
    private void setMensagemErro(String mensagemErro) {
        this.erro = mensagemErro;
    }

    public String getMensagemErro() {
        return erro;
    }
    
    public Token analisadorLexicoNivel1(String filePath) throws FileNotFoundException, IOException {
        Token token;
        ArrayList<Token> listaTokens = new ArrayList<>();
        
        if (bufferedReader == null) {
            if (!this.openFile(filePath)) {
                return null;
            } else {
                charRead = bufferedReader.read();
                currentChar = (char) charRead;
            }
        }

        try {
            while (!hasFileEnd()) { //trocar "while" por "if" depois
                while ((ignora.contains(currentChar)) && charRead != -1) {
                    if (currentChar == '{') {

                        while (currentChar != '}' && charRead != -1) {
                            charRead = bufferedReader.read();
                            currentChar = (char) charRead;
                        }

                        charRead = bufferedReader.read();
                        currentChar = (char) charRead;

                     } else if (currentChar == '/') {// achou primeiro /
                        //System.out.println(""+currentChar);
                        int k = 0; //sinaliza que achou 2° /
                        charRead = bufferedReader.read();
                        currentChar = (char) charRead;
                        //System.out.println(""+currentChar);
                        
                        if (currentChar != '*'){
                            System.out.println("\nERRO: Linha "+indexFileLine+"  |  '/' deve ser seguido por '*' para comentar.\n");
                            break;
                        } else { //achou 1°estrela
                            charRead = bufferedReader.read();
                            currentChar = (char) charRead;
                            
                            while(k==0 && charRead != -1){
                                while (currentChar != '*' && charRead != -1) {
                                    charRead = bufferedReader.read();
                                    currentChar = (char) charRead;
                                }
                                //achou 2° estrela
                                charRead = bufferedReader.read();
                                currentChar = (char) charRead;

                                if (currentChar == '/'){ //achou segunda barra;
                                    k++;
                                    charRead = bufferedReader.read();
                                    currentChar = (char) charRead;
                                }
                            }
                        
                            if(charRead == -1){
                                 System.out.println("\nERRO: Linha "+indexFileLine+"  |  comentário não foi fechado.\n");
                                 break;
                            }    
                        }
                    } else if (currentChar == '\n') {
                        indexFileLine++;
                        charRead = bufferedReader.read();
                        currentChar = (char) charRead;

                    } else {
                        charRead = bufferedReader.read();
                        currentChar = (char) charRead;

                        while (currentChar == ' ' && charRead != -1) {
                            charRead = bufferedReader.read();
                            currentChar = (char) charRead;
                        }
                    }
                }

                if (!hasFileEnd()) {
                    System.out.printf("[lexicalAnalyze] | Line: %d\n", indexFileLine);
                    System.out.printf("[lexicalAnalyze] | Character: %c\n", currentChar);
                    token = this.pegaToken(indexFileLine);
                    listaTokens.add(token);
                    return token;
                } else {
                    this.closeFile();
                }

            }//else {
             //   this.closeFile();
            //}

        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println("\nERRO: "+e.toString()+"\n");
            } else {
                System.out.println("\nERRO LÉXICO: Ocorreu algo inesperado.\n");
            }//Mais adiante, deixar a classe MensagensErro top e descomentar            
            /*if (e.getMessage() != null) {
                setMensagemErro(e.getMessage());
            } else {
                setMensagemErro("[ Lexical Error ] | Something unexpected happened");
            }*/
        }
        return null;
    }
    
    public Token pegaToken(int indexFile) throws IOException{
        Token t = new Token();

        if (Character.isDigit(currentChar)) {
            t = this.trataDigito(currentChar, indexFile);

        } else if (Character.isLetter(currentChar)) {
            t = this.trataIdPalvraReservada(currentChar, indexFile);

        } else if (currentChar == ':') {
            t = this.trataAtribuicao(currentChar, indexFile);

        } else if (aritmetico.contains(currentChar)) {
            t = this.trataOpAritmetico(currentChar, indexFile);

        } else if (relacional.contains(currentChar)) {
            t = this.trataOpRelacional(currentChar, indexFile);

        } else if (pontuacao.contains(currentChar)) {
            t = this.trataPontucao(currentChar, indexFile);

        } else { //Colocar na classe ERRO bonitinho
            System.out.println("\nERRO LÉXICO:  Linha "+indexFile+"  |  Caracter '"+currentChar+"' inválido! Não pertence ao alfabeto da LPD.\n");
            //t = null;
        }
        //System.out.println("token != null "+t.getSimbolo() +" "+t.getLexema());
        return t;
    }

    private Token trataDigito(char character, int lineIndex) throws IOException {
        Token digito = new Token();
        String numero = "";

        numero += character;
        charRead = bufferedReader.read();
        currentChar = (char) charRead;

        while (Character.isDigit(currentChar)) {
            numero += currentChar;
            charRead = bufferedReader.read();
            currentChar = (char) charRead;
        }

        digito.setSimbolo("snumero");
        digito.setLexema(numero);
        digito.setLinha(Integer.toString(lineIndex));
        
        return digito;
    }

    private Token trataIdPalvraReservada(char character, int lineIndex) throws IOException {
        Token letra = new Token();
        String palavra = "";

        palavra += character;
        charRead = bufferedReader.read();
        currentChar = (char) charRead;

        while (Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_') {
            palavra += currentChar;
            charRead = bufferedReader.read();
            currentChar = (char) charRead;
        }

        letra.setLinha(Integer.toString(lineIndex));

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
                letra.setSimbolo("sinicio");
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

    private Token trataAtribuicao(char character, int lineIndex) throws IOException {
        Token attribution = new Token();
        String attr = "";

        attr += character;
        attribution.setLinha(Integer.toString(lineIndex));
        
        charRead = bufferedReader.read();
        currentChar = (char) charRead;

        if (currentChar == '=') {
            attr += currentChar;
            attribution.setSimbolo("satribuicao");
            charRead = bufferedReader.read();
            currentChar = (char) charRead;

        } else {
            attribution.setSimbolo("sdoispontos");
        }

        attribution.setLexema(attr);
        return attribution;
    }

    private Token trataOpAritmetico(char character, int lineIndex) throws IOException {
        Token aritimetico = new Token();

        aritimetico.setLinha(Integer.toString(lineIndex));
        
        switch (character) {
            case '+':
                aritimetico.setSimbolo("smais");
                break;
            case '-':
                aritimetico.setSimbolo("smenos");
                break;
            case '*':
                aritimetico.setSimbolo("smult");
                break;
            /*case '/':         // "/" = div, no trataIdPalavraReservada
                aritimetico.setSimbolo("sdiv");
                break;*/
            default:
                //System.out.println("\nERRO LEXICO:  Linha "+lineIndex+" | Chamou o trataOpAritmetico errado! (???)\n");
                break;
        }

        aritimetico.setLexema(Character.toString(character));
        charRead = bufferedReader.read();
        currentChar = (char) charRead;
        
        return aritimetico;
    }

    private Token trataOpRelacional(char character, int lineIndex) throws IOException {
        Token relational = new Token();
        String operation = "";

        operation += character;
        relational.setLinha(Integer.toString(lineIndex));
        
        switch (character) {
            
            case '>':
                charRead = bufferedReader.read();
                currentChar = (char) charRead;
                
                if (currentChar == '=') {
                    operation += currentChar;
                    relational.setSimbolo("smaiorig");
                    
                    charRead = bufferedReader.read();
                    currentChar = (char) charRead;                    
                } else {
                    relational.setSimbolo("smaior");
                } break;
                
            case '<':
                charRead = bufferedReader.read();
                currentChar = (char) charRead;
                
                if (currentChar == '=') {
                    operation += currentChar;
                    relational.setSimbolo("smenorig");
                    
                    charRead = bufferedReader.read();
                    currentChar = (char) charRead;                    
                } else {
                    relational.setSimbolo("smenor");
                } break;
                
            case '=':
                relational.setSimbolo("sig");
                
                charRead = bufferedReader.read();
                currentChar = (char) charRead;
                
                break;
                
            case '!':
                charRead = bufferedReader.read();
                currentChar = (char) charRead;
                
                if (currentChar == '=') {
                    operation += currentChar;
                    relational.setSimbolo("sdif");
                    
                    charRead = bufferedReader.read();
                    currentChar = (char) charRead;                    
                } else {
                    System.out.println("\nERRO LEXICO: Linha "+lineIndex+"  |  O caracter '!' deve ser seguido de '='.\n");
                    //throw new Exception(message.lexicalError(Integer.toString(lineIndex), operation));
                } break;
                
            default:
                //System.out.println("\nERRO LEXICO: Linha "+lineIndex+"  |  Chamou o trataOpRelacional errado! (???)\n");
                break;
        }

        relational.setLexema(operation);
        return relational;
    }

    private Token trataPontucao(char character, int lineIndex) throws IOException {
        Token punctuation = new Token();

        punctuation.setLinha(Integer.toString(lineIndex));
        
        switch (character) {
            case ';':
                punctuation.setSimbolo("sponto_virgula");
                break;
            case ',':
                punctuation.setSimbolo("svirgula");
                break;
            case '(':
                punctuation.setSimbolo("sabre_parenteses");
                break;
            case ')':
                punctuation.setSimbolo("sfecha_parenteses");
                break;
            case '.':
                punctuation.setSimbolo("sponto");
                break;
            default:
                //System.out.println("\nERRO LEXICO: Linha "+lineIndex+"  |  Chamou o trataPontuacao errado! (???)\n");
                break;
        }

        punctuation.setLexema(Character.toString(character));
        charRead = bufferedReader.read();
        currentChar = (char) charRead;
        return punctuation;
    }
}
