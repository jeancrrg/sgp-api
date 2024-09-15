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
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

@Service
public class UploadArquivoServiceImpl implements UploadArquivoService {

    @Autowired
    private ConverterUtil converterUtil;

    @Value("${amazon.bucket}")
    private String BUCKET_AMAZON;

    public String buscarUrlArquivoAmazon(String caminhoDiretorio) throws UploadArquivoException {
        try {
            if (caminhoDiretorio == null || caminhoDiretorio.isEmpty()) {
                throw new BadRequestException("Caminho do diretório do arquivo não encontrado para buscar!");
            }
            // Usado recurdo try-with-resources para garantir o autoclose automático do S3Presigner
            try (S3Presigner presigner = S3Presigner.builder().region(Region.SA_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build()) {

                // Requisição de obtenção do objeto
                GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();

                // Criação da URL pré-assinada com expiração de 30 minutos
                GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder().signatureDuration(Duration.ofMinutes(30)).getObjectRequest(getObjectRequest).build();

                // Gera a URL pré-assinada
                URL presignedUrl = presigner.presignGetObject(presignRequest).url();

                // Retorna a URL
                return presignedUrl.toExternalForm();
            }
        } catch (BadRequestException e) {
            throw new UploadArquivoException(e.getMessage());
        } catch (Exception e) {
            throw new UploadArquivoException("ERRO: Erro ao buscar arquivo no diretório: " + caminhoDiretorio + " da amazon! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void realizarUploadAmazon(UploadArquivoDTO uploadArquivoDTO) throws UploadArquivoException {
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

    public void enviarArquivoAmazon(String caminhoDiretorio, byte[] arquivoBytes) throws Exception {
        // Usado recurso try-with-resources para garantir o autoclose automático do inputStream e s3Client
        try (InputStream inputStream = new ByteArrayInputStream(arquivoBytes);
                S3Client s3Client = S3Client.builder().region(Region.SA_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build()) {

            // Requisição de upload do objeto
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();

            // Envio do arquivo
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, arquivoBytes.length));
        } catch (Exception e) {
            throw new Exception("ERRO: Erro ao enviar arquivo para a amazon! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void verificarArquivoEnviado(String caminhoDiretorio) throws Exception {
        // Usado recurso try-with-resources para garantir o autoclose automático do s3Client
        try (S3Client s3Client = S3Client.builder().region(Region.SA_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build()) {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();
            s3Client.headObject(headObjectRequest);
        } catch (S3Exception e) {
            throw new Exception("ERRO: Arquivo não encontrado após realização do upload! - MENSAGEM DO ERRO: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("ERRO: Erro ao verificar se o arquivo foi enviado! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

}
