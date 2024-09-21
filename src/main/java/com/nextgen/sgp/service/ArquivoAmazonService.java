package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.dto.UploadArquivoDTO;
import com.nextgen.sgp.exception.ArquivoAmazonException;

public interface ArquivoAmazonService {

    String buscarUrlArquivoAmazon(String caminhoDiretorio) throws ArquivoAmazonException;

    void realizarUploadAmazon(UploadArquivoDTO uploadArquivo) throws ArquivoAmazonException;

    byte[] baixarArquivo(String caminhoDiretorio) throws ArquivoAmazonException;

}
