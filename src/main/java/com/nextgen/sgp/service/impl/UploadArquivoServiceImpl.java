package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.dto.UploadArquivoDTO;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.ConverterException;
import com.nextgen.sgp.exception.UploadArquivoException;
import com.nextgen.sgp.service.UploadArquivoService;
import com.nextgen.sgp.util.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class UploadArquivoServiceImpl implements UploadArquivoService {

    @Autowired
    private ConverterUtil converterUtil;

    @Value("${amazon.bucket}")
    private String BUCKET_AMAZON;

    public void realizarUpload(UploadArquivoDTO uploadArquivoDTO) throws UploadArquivoException {
        try {
            validarCamposUploadArquivo(uploadArquivoDTO);
            byte[] arquivoBytes = converterUtil.converterBase64EmBytes(uploadArquivoDTO.getArquivoBase64());
            enviarArquivoAmazon(uploadArquivoDTO.getCaminhoDiretorio(), arquivoBytes);
            verificarArquivoEnviado(uploadArquivoDTO.getCaminhoDiretorio());
        } catch (BadRequestException e) {
            throw new UploadArquivoException(e.getMessage());
        } catch (ConverterException e) {
            throw new UploadArquivoException("ERRO: Erro ao realizar conversão do arquivo: " + uploadArquivoDTO.getNomeArquivo() + "! - MENSAGEM DO ERRO: " + e.getMessage());
        } catch (Exception e) {
            throw new UploadArquivoException("ERRO: Erro ao realizar upload do arquivo: " + uploadArquivoDTO.getNomeArquivo() + "! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void validarCamposUploadArquivo(UploadArquivoDTO uploadArquivo) throws BadRequestException {
        if (uploadArquivo == null) {
            throw new BadRequestException("Arquivo não encontrado!");
        }
        if (uploadArquivo.getNomeArquivo() == null || uploadArquivo.getNomeArquivo().isEmpty()) {
            throw new BadRequestException("Nome do arquivo não encontrado!");
        }
        if (uploadArquivo.getCaminhoDiretorio() == null || uploadArquivo.getCaminhoDiretorio().isEmpty()) {
            throw new BadRequestException("Caminho de diretório do arquivo não encontrado!");
        }
        if (uploadArquivo.getArquivoBase64() == null || uploadArquivo.getArquivoBase64().isEmpty()) {
            throw new BadRequestException("Formato do arquivo não encontrado!");
        }
    }

    public void enviarArquivoAmazon(String caminhoDiretorio, byte[] arquivoBytes) {
        InputStream inputStream = new ByteArrayInputStream(arquivoBytes);

        // Criação do cliente S3
        S3Client s3Client = S3Client.builder().region(Region.SA_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build();

        // Requisição de upload do objeto
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();

        // Envio do arquivo
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, arquivoBytes.length));
    }

    public void verificarArquivoEnviado(String caminhoDiretorio) throws Exception {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();
            S3Client s3Client = S3Client.builder().region(Region.SA_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build();
            s3Client.headObject(headObjectRequest);
        } catch (S3Exception e) {
            throw new Exception("ERRO: Arquivo não encontrado após realização do upload! - MENSAGEM DO ERRO: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("ERRO: Erro ao verificar se o arquivo foi enviado! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
