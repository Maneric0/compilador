package controller;
import java.util.ArrayList;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<String> pilha_tipos = new Stack<>();
    private Stack<String> pilha_rotulos = new Stack<>();
    
    private StringBuilder codigo = new StringBuilder();
    
    private Token tokenAtual;
    
    private String operador_relacional = "";
    private String tipo = "";
    
    private ArrayList<String> lista_identificadores = new ArrayList<>();
    
    public void executeAction(int action, Token token)	throws SemanticError
    {
        tokenAtual = token;
        
        switch(action) {
            case 100 -> executar100();
            case 101 -> executar101();
            case 102 -> executar102();
            case 103 -> executar103();
            case 104 -> executar104();
            case 105 -> executar105();
            case 106 -> executar106();
            case 107 -> executar107();
            case 108 -> executar108();
            case 109 -> executar109();
            case 110 -> executar110();
            case 111 -> executar111(token);
            case 112 -> executar112(token);
            case 115 -> executar115();
            case 116 -> executar116();
            case 118 -> executar118();
        }
        
        //System.out.println("Ação #"+action+", Token: "+token);
    }
    
    public void executar100(){
        codigo.append(".assembly extern mscorlib {}\n");
        codigo.append(".assembly _programa{}\n");
        codigo.append(".module _programa.exe\n");
        codigo.append("\n");
        codigo.append(".class public _unica{\n");
        codigo.append(".method static public void _principal(){\n");
        codigo.append(".entrypoint{\n");
    }
    
    public void executar101(){
        codigo.append("ret\n");
        codigo.append("}\n");
        codigo.append("}\n");
        codigo.append("}\n");
    }
    
    public void executar102(){
        tipo = pilha_tipos.pop();
        if(tipo.equals("int64"))
            codigo.append("conv.i8\n");    
        pilha_tipos.push(tipo);
        tipo = "";
    }
    
    public void executar103(){
        pilha_tipos.push("int64");
        codigo.append("ldc.i8 \n");
        codigo.append(tokenAtual.getLexeme());
        codigo.append("\n");
        codigo.append("conv.r8\n");
    }
    
    public void executar104(){
        pilha_tipos.push("float64");
        codigo.append("ldc.r8 \n");
        codigo.append(tokenAtual.getLexeme());
        codigo.append("\n");
    }
    
    public void executar105(){
        pilha_tipos.push("string");
        codigo.append("ldstr \n");
        codigo.append(tokenAtual.getLexeme());
        codigo.append("\n");
    }
    
    public void executar106(){
        tipo = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();
        codigo.append("add");
        codigo.append("\n");
        
        if (tipo.equals(tipo2)) {
           pilha_tipos.push(tipo);
        }
        else {
            pilha_tipos.push("float64");
        }
    }
        
    public void executar107(){
        tipo = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();
        codigo.append("sub");
        codigo.append("\n");

        if (tipo.equals(tipo2)) {
           pilha_tipos.push(tipo);
        }
        else {
            pilha_tipos.push("float64");
        }
    }   
    
    public void executar108(){
        tipo = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();
        codigo.append("mul");
        codigo.append("\n");
        
        if (tipo.equals(tipo2)) {
           pilha_tipos.push(tipo);
        }
        else {
            pilha_tipos.push("float64");
        }
    }
    
    public void executar109(){
        tipo = pilha_tipos.pop();
        String tipo2 = pilha_tipos.pop();
        codigo.append("div");
        codigo.append("\n");

        pilha_tipos.push("float64");
        
    }

    public void executar110(){
        pilha_tipos.push("int64");
        codigo.append("ldc.i4 -1\n");
        codigo.append("mul \n");
        codigo.append("\n");
    }
    
    public void executar111(Token token){
        this.operador_relacional = token.getLexeme();
    }
    
    public void executar112(Token token) {
        pilha_tipos.pop();
        pilha_tipos.pop();

        pilha_tipos.push("bool");

        switch (this.operador_relacional) {

            case "==":
                codigo.append("ceq\n");
                break;

            case "~=":
                codigo.append("ceq\n");
                codigo.append("ldc.i4 0\n");
                codigo.append("ceq\n");
                break;

            case "<":
                codigo.append("clt\n");
                break;

            case ">":
                codigo.append("cgt\n");
                break;

            default:
                break;
        }
    }
    
    public void executar115(){
        pilha_tipos.push("bool");
        codigo.append("ldc.i4 1\n");
        codigo.append("\n");
    }

    public void executar116(){
        pilha_tipos.push("bool");
        codigo.append("ldc.i4 0\n");
        codigo.append("\n");
    }
    
    public void executar118(){
        tipo = pilha_tipos.pop();
        codigo.append("call void [mscorlib]System.Console::Write("+ tipo +")\n");
        tipo = "";
    }


    public StringBuilder getCodigo() {
        return codigo;
    }
    
    
}