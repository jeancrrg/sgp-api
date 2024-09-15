package com.nextgen.sgp.util;

import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.ConverterException;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ConverterUtil {

    public byte[] converterBase64EmBytes(String base64) throws ConverterException {
        try {
            if (base64 == null || base64.isEmpty()) {
                throw new BadRequestException("Base 64 não encontrado!");
            }
            return Base64.getDecoder().decode(base64.substring(base64.indexOf(",") + 1));
        } catch (Exception e) {
            throw new ConverterException("ERRO: Erro ao converter base 64 em bytes! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
