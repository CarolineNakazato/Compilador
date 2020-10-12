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
    //erro lexico
     public String digito(String line, char character) {
        messageError = "[DigitError] Line: " + line + " Character: " + character + " | Invalid Digit Format";
        return messageError;
    }

    public String letra(String line, char character) {
        messageError = "[LetterError] Line: " + line + " Character: " + character + " | Invalid Word Format";
        return messageError;
    }

    public String atribuicao(String line, char character) {
        messageError = "[AttributionError] Line: " + line + " Character: " + character + " | Invalid Attribution Format";
        return messageError;
    }

    public String aritimedico(String line, char character) {
        messageError = "[AritmeticError] Line: " + line + " Character: " + character + " | Invalid Aritmetic Format";
        return messageError;
    }

    public String relacional(String line, String character) {
        messageError = "[RelationalError] Line: " + line + " Character: " + character + " | Invalid Relational Format";
        return messageError;
    }

    public String pontuacao(String line, char character) {
        messageError = "[PunctuationError] Line: " + line + " Character: " + character + " | Invalid Punctuation Format";
        return messageError;
    }

    public String caracterInvalido(String line, char character) {
        messageError = "[CharacterError] Line: " + line + " Character: " + character + " | Invalid Character";
        return messageError;
    }

    // Erro sintatico  
    public String programError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sprograma expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String identifierError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sidentificador expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String semicolonError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sponto_virgula expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String dotError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sponto expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String colonError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol received not expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String typeError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sinteiro or sbooleano expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String beginError(String method, Token wrongToken) {
        messageError = "[" + method + "] | symbol sinicio expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String booleanError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sverdadeiro or sfalso expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String openparenthesesError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sabre_parenteses expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String closeparenthesesError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sfecha_parenteses expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String doError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sfaca expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String thenError(String method, Token wrongToken) {
        messageError = "[" + method + "] | Symbol sentao expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }

    public String endoffileError(String method, Token wrongToken) {
        messageError = "[" + method + "] | End of file expected, symbol received: " 
                + wrongToken.getSimbolo()+ "\n| Line: " + wrongToken.getLinha();
        return messageError;
    }
}
