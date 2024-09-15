package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.dto.UploadArquivoDTO;
import com.nextgen.sgp.exception.UploadArquivoException;

public interface UploadArquivoService {

    String buscarUrlArquivoAmazon(String caminhoDiretorio) throws UploadArquivoException;

    void realizarUploadAmazon(UploadArquivoDTO uploadArquivo) throws UploadArquivoException;

}
