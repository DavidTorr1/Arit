package analizador_sintactico1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.List;

public class Parser {

    private final List<Token> tokens;

    private final Token identificador = new Token(TipoToken.NUMERO, "");
private final Token finCadena = new Token(TipoToken.EOF, "");
private final Token parentesisAbre = new Token(TipoToken.PARENI, "(");
private final Token parentesisCierra = new Token(TipoToken.PAREND, ")");
private final Token punto = new Token(TipoToken.PUNTO, ")");
private final Token suma = new Token(TipoToken.SUMA, "+");
private final Token resta = new Token(TipoToken.RESTA, "-");
private final Token multiplicacion = new Token(TipoToken.MULTIPLICACION, "*");
private final Token division = new Token(TipoToken.DIVISION, "/");


    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        

         if (!hayErrores && !preanalisis.equals(finCadena)) {
        int resultado = expresion();
        System.out.println(resultado);
         }
        else if(!hayErrores && preanalisis.equals(finCadena)){       
            System.out.println("Consulta válida");
        }
        else{
            System.out.println("Error en la posición " + ". No se esperaba el token " + preanalisis.tipo);
        }

        /*if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }
    private int evaluarOperacion(Token operador, int operando1, int operando2) {
    if (operador.tipo == TipoToken.SUMA) {
        return operando1 + operando2;
    } else if (operador.tipo == TipoToken.RESTA) {
        return operando1 - operando2;
    } else if (operador.tipo == TipoToken.MULTIPLICACION) {
        return operando1 * operando2;
    } else if (operador.tipo == TipoToken.DIVISION) {
        if (operando2 != 0) {
            return operando1 / operando2;
        } else {
            // Manejar la división por cero, por ejemplo, lanzando una excepción
            throw new ArithmeticException("División por cero");
        }
    }
    return 0; // En caso de que el operador no sea válido (esto puede personalizarse)
}
    private int expresion() {
    int resultado = termino();
    while (preanalisis.tipo == TipoToken.SUMA || preanalisis.tipo == TipoToken.RESTA) {
        Token operador = preanalisis;
        match(preanalisis.tipo); // Consume el operador
        int operando2 = termino();
        resultado = evaluarOperacion(operador, resultado, operando2);
    }
    return resultado;
}

private int termino() {
    int resultado = factor();
    while (preanalisis.tipo == TipoToken.MULTIPLICACION || preanalisis.tipo == TipoToken.DIVISION) {
        Token operador = preanalisis;
        match(preanalisis.tipo); // Consume el operador
        int operando2 = factor();
        resultado = evaluarOperacion(operador, resultado, operando2);
    }
    return resultado;
}

private int factor() {
    int resultado = 0;
    if (preanalisis.tipo == TipoToken.NUMERO) {
        resultado = Integer.parseInt(preanalisis.lexema);
        match(TipoToken.NUMERO); // Consume el número
    } else if (preanalisis.tipo == TipoToken.PARENI) {
        match(TipoToken.PARENI);
        resultado = expresion();
        match(TipoToken.PAREND);
    } else {
        // Manejar otros casos, si es necesario
    }
    return resultado;
}

private void match(TipoToken tipoEsperado) {
    if (preanalisis.tipo == tipoEsperado) {
        // Consume el token actual
        if (i < tokens.size() - 1) {
            i++;
            preanalisis = tokens.get(i);
        } else {
            // Llegaste al final de la lista de tokens
            preanalisis = new Token(TipoToken.EOF, "");
        }
    } else {
        // Error de sintaxis, el token actual no coincide con el esperado
        reportarError("Se esperaba un token de tipo " + tipoEsperado);
    }
}

private void reportarError(String mensaje) {
    System.out.println("Error en la posición " + preanalisis + ": " + mensaje);
    hayErrores = true;
}
    
    
    
}
