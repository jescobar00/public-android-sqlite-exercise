package com.utn.tp4.util;

import java.util.regex.Pattern;

public class Utilitario {

    public boolean esSoloLetras(String textoVerificar){
        final String REGEX_LETRAS = "^[a-zA-Z ]+$";
        Pattern patron = Pattern.compile(REGEX_LETRAS);
        return patron.matcher(textoVerificar).matches();
    }

    public boolean esSoloNros(String textoVerificar){
        final String REGEX_NUMEROS = "^[0-9]+$";
        Pattern patron = Pattern.compile(REGEX_NUMEROS);
        if(patron.matcher(textoVerificar).matches()){
            return true;
        }else{
            return false;
        }
    }

}
