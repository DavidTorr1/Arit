/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_sintactico1;

public enum TipoToken {
    NUMERO, 

    // Palabras reservadas
    SUMA, RESTA, MULTIPLICACION, DIVISION, IGUAL,PUNTO,

    // Caracteres
    PARENI, PAREND,

    // Final de cadena
    EOF
}
