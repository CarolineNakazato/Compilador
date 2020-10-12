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
public class AnalisadorSintatico {
    private static AnalisadorSintatico instance = null;
    private String arq;
    private int label;

    private final AnalisadorLexico lexico;

    private Token token;
    //private Token buffer;
    private TabelaSimbolo tabela;

    private final MensagensErro message;
    //private String erro;
    //private String erroLexico;

    public static AnalisadorSintatico getInstance() {
        if (instance == null) {
            instance = new AnalisadorSintatico();
        }
        return instance;
    }

    private AnalisadorSintatico() {
        this.lexico = AnalisadorLexico.getInstance();
        this.message = MensagensErro.getInstance();
        this.tabela = TabelaSimbolo.getInstance();
        this.arq = null;
        //this.label = 1;
        this.token = null;
    }
    
     protected void recebeArq(String arq) {
        this.arq = arq;
    }

    private boolean isEmpty(Token receivedToken) {
        if (receivedToken == null) {
            return true;
        }
        return false;
    }

    /*private void setError(String error) {
        this.erro = error;
    }

    public String getError() {
        return this.erro;
    }*/

    /*
     *  Analisador Sintatico
     */
    public void analiseSintatica() {
        String scopeProgram;
        ProcedureProgram symbolProgram = new ProcedureProgram();

         try {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                if (token.getSimbolo().equals("sprograma")) {
                    token = lexico.analisadorLexicoNivel1(arq);
                    token.print();

                    if (!isEmpty(token)) {
                        if (token.getSimbolo().equals("sidentificador")) {
                            symbolProgram.setLexema(token.getLexema());
                            symbolProgram.setEscopo("");
                            tabela.inserirSimbolo(symbolProgram);

                            scopeProgram = token.getLexema();
                            token = lexico.analisadorLexicoNivel1(arq);
                            token.print();

                            if (!isEmpty(token)) {
                                if (token.getSimbolo().equals("sponto_vírgula")) {
                                    analisaBloco(scopeProgram);

                                    if (token.getSimbolo().equals("sponto")) {
                                        
                                        token = lexico.analisadorLexicoNivel1(arq);
                                        
                                        if (!lexico.hasFileEnd()) {
                                            //throw new Exception(message.endoffileError("syntaticAnalyze", token));
                                        }
                                    } else {
                                        //throw new Exception(message.dotError("syntaticAnalyze", token));
                                    }

                                } else {
                                    //throw new Exception(message.semicolonError("syntaticAnalyze", token));
                                }

                            } else {
                                throw new Exception();
                            }

                        } else {
                            //throw new Exception(message.identifierError("syntaticAnalyze", token));
                        }

                    } else {
                        throw new Exception();
                    }

                } else {
                    //throw new Exception(message.programError("syntaticAnalyze", token));
                }

            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
            }

            System.out.println("[AnalisadorSintatico] | Erro");
            //System.out.println("[syntaticAnalyze] | Ending compilation process");
        }

        System.out.println("\n[syntaticAnalyze] | Symbol Table:");
        tabela.printTabela();
        symbolProgram = null;
    }

   private void analisaBloco(String scope) throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            analisaDeclaracaoDeVariaveis(scope);
            analisaDeclaracaoDeSubRotinas(scope);
            analisaComandos();
        } else {
            throw new Exception();
        }
    }

    private void analisaDeclaracaoDeVariaveis(String scope) throws Exception { 
        if (token.getSimbolo().equals("svar")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                if (token.getSimbolo().equals("sidentificador")) {
                    while (token.getSimbolo().equals("sidentificador")) {
                        analisaVariaveis(scope);

                        if (token.getSimbolo().equals("sponto_vírgula")) {
                            token = lexico.analisadorLexicoNivel1(arq);
                            token.print();

                            if (isEmpty(token)) {
                                throw new Exception();
                            }

                        } else {
                            //throw new Exception(message.semicolonError("analyzeVariablesDeclaration", token));
                        }
                    }

                } else {
                    //throw new Exception(message.identifierError("analyzeVariablesDeclaration", token));
                }
            } else {
                throw new Exception();
            }
        }
    }

    private void analisaVariaveis(String scope) throws Exception {
       while (!token.getSimbolo().equals("sdoispontos")) {
            Variavel simbolo = new Variavel();

            if (token.getSimbolo().equals("sidentificador")) {
                simbolo.setLexema(token.getLexema());
                simbolo.setEscopo(scope);
                tabela.inserirSimbolo(simbolo);

                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (!isEmpty(token)) {
                    if (token.getSimbolo().equals("svirgula") || token.getSimbolo().equals("sdoispontos")) {
                        if (token.getSimbolo().equals("svirgula")) {

                            token = lexico.analisadorLexicoNivel1(arq);
                            token.print();

                            if (!isEmpty(token)) {
                                if (token.getSimbolo().equals("sdoispontos")) {
                                    //throw new Exception(message.colonError("analyzeVariables", token));
                                }

                            } else {
                                throw new Exception();
                            }
                        }
                    }

                } else {
                    throw new Exception();
                }
            }
            simbolo = null;
        }

        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            analisaTipo();
        } else {
            throw new Exception();
        }
    }

    private void analisaDeclaracaoDeSubRotinas(String scope) throws Exception {
        int flag = 0;
        
        if (token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")) {
            flag = 1;
        }

        while (token.getSimbolo().equals("sprocedimento") || token.getSimbolo().equals("sfuncao")) {
            if (token.getSimbolo().equals("sprocedimento")) {
                analisaDeclaracaoDeProcedimento(scope);
            } else {
                analisaDeclaracaoDeFuncao(scope);
            }

            if (token.getSimbolo().equals("sponto_vírgula")) {
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (isEmpty(token)) {
                    throw new Exception();
                }

            } else {
                //throw new Exception(message.semicolonError("analyzeSubRoutineDeclaration", token));
            }
        }

        if (flag == 1) {
            //Gera(auxrot,NULL,´ ´,´ ´) {início do principal} 
        }
    }

    private void analisaDeclaracaoDeProcedimento(String scope) throws Exception {
        String scopeProcedure;
        ProcedureProgram symbolProcedure = new ProcedureProgram();

        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            if (token.getSimbolo().equals("sidentificador")) {
                symbolProcedure.setLexema(token.getLexema());
                symbolProcedure.setEscopo(scope);
                //Pesquisa
                //Inserir tabela de símbolos
                tabela.inserirSimbolo(symbolProcedure);
                //Gera rótulo

                scopeProcedure = token.getLexema();
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (!isEmpty(token)) {
                    if (token.getSimbolo().equals("sponto_vírgula")) {
                        analisaBloco(scopeProcedure);
                    } else {
                        //throw new Exception(message.semicolonError("analyzeProcedureDeclaration", token));
                    }

                } else {
                    throw new Exception();
                }

            } else {
                //throw new Exception(message.identifierError("analyzeProcedureDeclaration", token));
            }

        } else {
            throw new Exception();
        }

        symbolProcedure = null;
    }

    private void AnalisaChamadaDeProcedimento() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {

        } else {
            throw new Exception();
        }
    }

    private void analisaDeclaracaoDeFuncao(String scope) throws Exception {
        String scopeFunction;
        Funcao simbolo = new Funcao();

        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            //Label
            if (token.getSimbolo().equals("sidentificador")) {
                simbolo.setLexema(token.getLexema());
                simbolo.setEscopo(scope);
                tabela.inserirSimbolo(simbolo);

                scopeFunction = token.getLexema();
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (!isEmpty(token)) {
                    if (token.getSimbolo().equals("sdoispontos")) {
                        token = lexico.analisadorLexicoNivel1(arq);
                        token.print();

                        if (!isEmpty(token)) {
                            if (token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")) {
                               /*if (token.getSimbolo().equals("sinteiro")) {
                                } else {
                                }*/

                                token = lexico.analisadorLexicoNivel1(arq);
                                token.print();

                                if (!isEmpty(token)) {
                                    if (token.getSimbolo().equals("sponto_vírgula")) {
                                        analisaBloco(scopeFunction);
                                    } else {
                                       // throw new Exception(message.semicolonError("analyzeFunctionDeclaration", token));
                                    }

                                } else {
                                    throw new Exception();
                                }

                            } else {
                                //throw new Exception(message.typeError("analyzeFunctionDeclaration", token));
                            }

                        } else {
                            throw new Exception();
                        }

                    } else {
                        //throw new Exception(message.colonError("analyzeFunctionDeclaration", token));
                    }

                } else {
                    throw new Exception();
                }

            } else {
               // throw new Exception(message.identifierError("analyzeFunctionDeclaration", token));
            }

        } else {
            throw new Exception();
        }

        simbolo = null;
    }

   
    private void analisaComandos() throws Exception {
        if (token.getSimbolo().equals("sinício")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                analisaComando();

                while (!token.getSimbolo().equals("sfim")) {
                    if (token.getSimbolo().equals("sponto_vírgula")) {
                        token = lexico.analisadorLexicoNivel1(arq);
                        token.print();

                        if (!isEmpty(token)) {
                            if (!token.getSimbolo().equals("sfim")) {
                                analisaComando();
                            }

                        } else {
                            throw new Exception();
                        }

                    } else {
                        //throw new Exception(message.semicolonError("analyzeCommands", token));
                    }
                }
                
                token = lexico.analisadorLexicoNivel1(arq);
                
            } else {
                throw new Exception();
            }

        } else {
            //throw new Exception(message.beginError("analyzeCommands", token));
        }
    }

    private void analisaComando() throws Exception {
        if (token.getSimbolo().equals("sidentificador")) {
            analisaAtribChProcedimento();
        } else if (token.getSimbolo().equals("sse")) {
            analisaSe();
        } else if (token.getSimbolo().equals("senquanto")) {
            analisaEnquanto();
        } else if (token.getSimbolo().equals("sleia")) {
            analisaLeia();
        } else if (token.getSimbolo().equals("sescreva")) {
            analisaEscreva();
        } else {
            analisaComandos();
        }
    }

    private void analisaAtribChProcedimento() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {

            if (token.getSimbolo().equals("satribuição")) {
                analisaAtribuicao();
            } else {
                //analisaChamadaProcedimento();
            }
        } else {
            throw new Exception();
        }
    }

    private void analisaLeia() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            if (token.getSimbolo().equals("sabre_parênteses")) {
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (!isEmpty(token)) {
                    if (token.getSimbolo().equals("sidentificador")) {
                        token = lexico.analisadorLexicoNivel1(arq);
                        token.print();

                        if (!isEmpty(token)) {
                            if (token.getSimbolo().equals("sfecha_parênteses")) {
                                token = lexico.analisadorLexicoNivel1(arq);
                                token.print();

                                if (isEmpty(token)) {
                                    throw new Exception();
                                }

                            } else {
                                //throw new Exception(message.closeparenthesesError("analyzeRead", token));
                            }
                        }
                        // senao throw new SyntacticException();
                    } else {
                        //throw new Exception(message.identifierError("analyzeRead", token));
                    }

                } else {
                    throw new Exception();
                }

            } else {
                //throw new Exception(message.openparenthesesError("analyzeRead", token));
            }

        } else {
            throw new Exception();
        }
    }

    private void analisaEscreva() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (token.getSimbolo().equals("sabre_parênteses")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (token.getSimbolo().equals("sidentificador")) {
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (token.getSimbolo().equals("sfecha_parênteses")) {
                    token = lexico.analisadorLexicoNivel1(arq);
                    token.print();

                    if (isEmpty(token)) {
                        throw new Exception();
                    }

                } else {
                    //throw new Exception(message.closeparenthesesError("analyzeWrite", token));
                }
                // senao throw new SyntacticException();
            } else {
                //throw new Exception(message.identifierError("analyzeWrite", token));
            }

        } else {
            //throw new Exception(message.openparenthesesError("analyzeWrite", token));
        }
    }

    private void analisaEnquanto() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            analisaExpressao();

            if (token.getSimbolo().equals("sfaca")) {
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (!isEmpty(token)) {
                    analisaComando();
                } else {
                    throw new Exception();
                }

            } else {
                //throw new Exception(message.doError("analyzeWhile", token));
            }

        } else {
            throw new Exception();
        }
    }

    private void analisaSe() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (!isEmpty(token)) {
            analisaExpressao();

            if (token.getSimbolo().equals("sentao")) {
                token = lexico.analisadorLexicoNivel1(arq);
                token.print();

                if (!isEmpty(token)) {
                    analisaComando();

                    if (token.getSimbolo().equals("ssenao")) {
                        token = lexico.analisadorLexicoNivel1(arq);
                        token.print();

                        if (!isEmpty(token)) {
                            analisaComando();
                        } else {
                            throw new Exception();
                        }
                    }

                } else {
                    throw new Exception();
                }

            } else {
                //throw new Exception(message.thenError("analyzeIf", token));
            }

        } else {
            throw new Exception();
        }
    }

    private void analisaAtribuicao() throws Exception {
        token = lexico.analisadorLexicoNivel1(arq);

        if (!isEmpty(token)) {
            analisaExpressao();
        } else {
            throw new Exception();
        }
    }
    
    private void analisaTipo() throws Exception {
         if (token.getSimbolo().equals("sinteiro") || token.getSimbolo().equals("sbooleano")) {
            //table.setTypeSymbols(token.getLexeme());
        } else {
            //throw new Exception(message.typeError("analyzeType", token));
        }

        token = lexico.analisadorLexicoNivel1(arq);
        token.print();

        if (isEmpty(token)) {
            throw new Exception();
        }
    }

    private void analisaExpressao() throws Exception {
       analisaExpressaoSimples();

        if ((token.getSimbolo().equals("smaior")) || (token.getSimbolo().equals("smaiorig")) || 
            (token.getSimbolo().equals("sig"))    || (token.getSimbolo().equals("smenor"))   || 
            (token.getSimbolo().equals("smenorig")) || (token.getSimbolo().equals("sdif"))) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                analisaExpressaoSimples();
            } else {
                throw new Exception();
            }
        }
    }

    private void analisaExpressaoSimples() throws Exception {
        if (token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (isEmpty(token)) {
                throw new Exception();
            }
        }
        analisaTermo();

        while (token.getSimbolo().equals("smais") || token.getSimbolo().equals("smenos") || 
               token.getSimbolo().equals("sou")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                analisaTermo();
            } else {
                throw new Exception();
            }
        }
    }

    private void analisaTermo() throws Exception {
       analisaFator();

        while ((token.getSimbolo().equals("smult")) || (token.getSimbolo().equals("sdiv")) || 
               (token.getSimbolo().equals("se"))) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                analisaFator();
            } else {
                throw new Exception();
            }
        }
    }

    private void analisaFator() throws Exception {
        if (token.getSimbolo().equals("sidentificador")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

        } else if (token.getSimbolo().equals("snumero")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (isEmpty(token)) {
                throw new Exception();
            }

        } else if (token.getSimbolo().equals("snao")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                analisaFator();
            } else {
                throw new Exception();
            }

        } else if (token.getSimbolo().equals("sabre_parênteses")) {
            token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (!isEmpty(token)) {
                analisaExpressao();

                if (token.getSimbolo().equals("sfecha_parênteses")) {
                    token = lexico.analisadorLexicoNivel1(arq);
                    token.print();

                    if (isEmpty(token)) {
                        throw new Exception();
                    }

                } else {
                    //throw new Exception(message.closeparenthesesError("analyzeFactor", token));
                }
            } else {
                throw new Exception();
            }

        } else if (token.getSimbolo().equals("sverdadeiro") || token.getSimbolo().equals("sfalso")) {
           token = lexico.analisadorLexicoNivel1(arq);
            token.print();

            if (isEmpty(token)) {
                throw new Exception();
            }

        } else {
           //throw new Exception(message.booleanError("analyzeFactor", token));
        }
    }
}
