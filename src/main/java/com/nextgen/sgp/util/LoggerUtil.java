package com.nextgen.sgp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {

    public void info(Class<?> classe, String mensagem, String nomeMetodo) {
        Logger LOGGER = LoggerFactory.getLogger(classe);
        LOGGER.info(STR."MÉTODO: \{nomeMetodo} - MENSAGEM: \{mensagem}");
    }

    public void debug(Class<?> classe, String mensagemErro, String nomeMetodo, Exception excecao) {
        Logger LOGGER = LoggerFactory.getLogger(classe);
        LOGGER.error(STR."ERRO: \{mensagemErro} - MÉTODO DO ERRO: \{nomeMetodo} - TIPO DO ERRO: \{excecao.getClass().getSimpleName()} - CAUSA DO ERRO: \{excecao.getCause()} - MENSAGEM DO ERRO: \{excecao.getMessage()}");
    }

    public void error(Class<?> classe, String mensagemErro, String nomeMetodo, Exception excecao) {
        Logger LOGGER = LoggerFactory.getLogger(classe);
        LOGGER.error(STR."ERRO: \{mensagemErro} - MÉTODO DO ERRO: \{nomeMetodo} - TIPO DO ERRO: \{excecao.getClass().getSimpleName()} - CAUSA DO ERRO: \{excecao.getCause()} - MENSAGEM DO ERRO: \{excecao.getMessage()}");
    }

}
