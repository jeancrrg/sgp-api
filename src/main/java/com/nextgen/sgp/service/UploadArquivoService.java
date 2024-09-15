package com.nextgen.sgp.service;

import com.nextgen.sgp.domain.dto.UploadArquivoDTO;
import com.nextgen.sgp.exception.UploadArquivoException;

public interface UploadArquivoService {

    void realizarUpload(UploadArquivoDTO uploadArquivo) throws UploadArquivoException;

}
