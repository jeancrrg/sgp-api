package com.nextgen.sgp.util;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class FormatterUtil {

    public String removerAcentos(String texto) {
        String textoFormatado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return textoFormatado.replaceAll("[^\\p{ASCII}]", "");
    }

}
