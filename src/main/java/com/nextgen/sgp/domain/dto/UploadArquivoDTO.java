package com.nextgen.sgp.domain.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UploadArquivoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String nomeArquivo;

    private String caminhoDiretorio;

    private String arquivoBase64;

    public UploadArquivoDTO() {

    }

    public UploadArquivoDTO(String nomeArquivo, String caminhoDiretorio, String base64) {
        this.nomeArquivo = nomeArquivo;
        this.caminhoDiretorio = caminhoDiretorio;
        this.arquivoBase64 = base64;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getCaminhoDiretorio() {
        return caminhoDiretorio;
    }

    public void setCaminhoDiretorio(String caminhoDiretorio) {
        this.caminhoDiretorio = caminhoDiretorio;
    }

    public String getArquivoBase64() {
        return arquivoBase64;
    }

    public void setArquivoBase64(String arquivoBase64) {
        this.arquivoBase64 = arquivoBase64;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadArquivoDTO that = (UploadArquivoDTO) o;
        return Objects.equals(nomeArquivo, that.nomeArquivo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nomeArquivo);
    }

}
