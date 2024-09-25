package com.nextgen.sgp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {

    public void info(String chaveLog, String mensagem, String nomeMetodo, Class<?> classe) {
        Logger LOGGER = LoggerFactory.getLogger(classe);
        LOGGER.info(STR."[\{chaveLog}] - MÉTODO: \{nomeMetodo} - MENSAGEM: \{mensagem}");
    }

    public void debug(String chaveLog, String mensagemErro, String nomeMetodo, Exception excecao, Class<?> classe) {
        Logger LOGGER = LoggerFactory.getLogger(classe);
        LOGGER.debug(STR."[\{chaveLog}] - ERRO: \{mensagemErro} - MÉTODO DO ERRO: \{nomeMetodo} - TIPO DO ERRO: \{excecao.getClass().getSimpleName()} - MENSAGEM DO ERRO: \{excecao.getMessage()}");
    }

    public void error(String mensagemErro, String nomeMetodo, Exception excecao, Class<?> classe) {
        Logger LOGGER = LoggerFactory.getLogger(classe);
        LOGGER.error(STR."ERRO: \{mensagemErro} - MÉTODO DO ERRO: \{nomeMetodo} - TIPO DO ERRO: \{excecao.getClass().getSimpleName()} - MENSAGEM DO ERRO: \{excecao.getMessage()}");
    }

}
