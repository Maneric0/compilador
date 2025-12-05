package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Semantico implements Constants
{
    private Stack<String> pilha_tipos = new Stack<>();
    private Stack<String> pilha_rotulos = new Stack<>();
    private Map<String, String> tabelaSimbolos = new HashMap<>();

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
            case 119 -> executar119();
            case 120 -> executar120(token);
            case 121 -> executar121(token);
            case 122 -> executar122();
            case 123 -> executar123(token);
            case 124 -> executar124(token);
            case 125 -> executar125(token);
            case 126 -> executar126(token);
            case 127 -> executar127(token);
            case 128 -> executar128();
            case 129 -> executar129(token);
            case 130 -> executar130(token);

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

    public void executar119(){
    if (lista_identificadores == null || lista_identificadores.isEmpty()) {
        return;
    }

    String tipoIL = "";
    switch (this.tipo) {
        case "int":
            tipoIL = "int64";
            break;
        case "float":
            tipoIL = "float64";
            break;
        case "string":
            tipoIL = "string";
            break;
        case "bool":
            tipoIL = "bool";
            break;
        default:
            break;
    }
    codigo.append(".locals init ( ");

    for (int i = 0; i < lista_identificadores.size(); i++) {
        String id = lista_identificadores.get(i);

        tabelaSimbolos.put(id, tipoIL);

        codigo.append(tipoIL)
              .append(" ")
              .append(id);

        if (i < lista_identificadores.size() - 1) {
            codigo.append(", ");
        } else {
            codigo.append(" ");
        }
    }

    codigo.append(")\n");

    lista_identificadores.clear();
    this.tipo = "";
        
         
    }
    
    public void executar120(Token token){
       this.tipo = token.getLexeme();
    }
    
    public void executar121(Token token){
      lista_identificadores.add(token.getLexeme());
    }
    
    public void executar122() {
    String tipoLocal = pilha_tipos.pop();

    if (tipoLocal.equals("int64")) {
        codigo.append("conv.i8\n");
    }

    String id = lista_identificadores.get(0);

    codigo.append("stloc ").append(id).append("\n");

    lista_identificadores.remove(0);
}
    
    public void executar123(Token token) throws SemanticError  {

    String id = token.getLexeme();

    if (tabelaSimbolos.containsKey(id) && tabelaSimbolos.get(id).equals("bool")) {
        throw new SemanticError (id + " inválido para comando de entrada");
    }

    codigo.append("call string [mscorlib]System.Console::ReadLine()\n");

    String tipoVar = tabelaSimbolos.get(id);

    if (tipoVar.equals("int64")) {
        codigo.append("call int32 [mscorlib]System.Int32::Parse(string)\n");
        codigo.append("conv.i8\n");
    }
    else if (tipoVar.equals("float64")) {
        codigo.append("call float64 [mscorlib]System.Double::Parse(string)\n");
    }
    else if (tipoVar.equals("bool")) {
        codigo.append("call bool [mscorlib]System.Boolean::Parse(string)\n");
    }

    codigo.append("stloc " + id + "\n");
}
    
    public void executar124(Token token) {

    String palavra = token.getLexeme();

    codigo.append("ldstr ").append(palavra).append("\n");
    codigo.append("call void [mscorlib]System.Console::Write(string)\n");
}
    public void executar125(Token token) throws SemanticError {

    String tipoLocal = pilha_tipos.pop();

    if (!tipoLocal.equals("bool")) {
        throw new SemanticError("expressão incompatível em comando de seleção", token.getPosition());
    }

    String novoRotulo;
    if (pilha_rotulos.size() == 0) {
        novoRotulo = "novoRotulo1";
    } else {
        novoRotulo = "novoRotulo" + (pilha_rotulos.size() + 1);
    }

    codigo.append("brfalse ").append(novoRotulo).append("\n");

    pilha_rotulos.push(novoRotulo);
}

    public void executar126(Token token) {

    String rotuloFim = pilha_rotulos.pop();

    codigo.append(rotuloFim).append(":\n");
}
    
    public void executar127(Token token) {

    String rotuloFim = "fimRotulo" + (pilha_rotulos.isEmpty() ? "1" : (pilha_rotulos.size() + 1));

    String rotuloElse = pilha_rotulos.pop();

    codigo.append("br ").append(rotuloFim).append("\n");
    codigo.append(rotuloElse).append(":\n");

    pilha_rotulos.push(rotuloFim);
}

    public void executar128() {

    String novoRotulo = "novoRotulo" + (pilha_rotulos.isEmpty() ? "1" : (pilha_rotulos.size() + 1));

    codigo.append(novoRotulo).append(":\n");

    pilha_rotulos.push(novoRotulo);
}

    public void executar129(Token token) throws SemanticError {

    String tipo = pilha_tipos.pop();

    if (!tipo.equals("bool")) {
        throw new SemanticError("expressão incompátivel em comando de repetição", token.getPosition());
    }

    String rotuloInicio = pilha_rotulos.pop();

    codigo.append("brfalse ").append(rotuloInicio).append("\n");
}
    public void executar130(Token token) {

    String id = token.getLexeme();
    String tipoVar = tabelaSimbolos.get(id);

    pilha_tipos.push(tipoVar);

    codigo.append("ldloc ").append(id).append("\n");

    if (tipoVar.equals("int64")) {
        codigo.append("conv.r8\n");
    }
}

    public StringBuilder getCodigo() {
        return codigo;
    }
    
    
}