package com.nextgen.sgp.service.impl;

import com.nextgen.sgp.domain.dto.UploadArquivoDTO;
import com.nextgen.sgp.exception.BadRequestException;
import com.nextgen.sgp.exception.ConverterException;
import com.nextgen.sgp.exception.ArquivoAmazonException;
import com.nextgen.sgp.service.ArquivoAmazonService;
import com.nextgen.sgp.util.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
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
public class ArquivoAmazonServiceImpl implements ArquivoAmazonService {

    @Autowired
    private ConverterUtil converterUtil;

    @Value("${amazon.bucket}")
    private String BUCKET_AMAZON;

    @Value("${amazon.region}")
    private String REGION_AMAZON;

    public String buscarUrlArquivoAmazon(String caminhoDiretorio) throws ArquivoAmazonException {
        try {
            if (caminhoDiretorio == null || caminhoDiretorio.isBlank()) {
                throw new BadRequestException("Caminho do diretório do arquivo não encontrado para buscar!");
            }
            // Usado recurdo try-with-resources para garantir o autoclose automático do S3Presigner
            try (S3Presigner presigner = S3Presigner.builder().region(Region.of(REGION_AMAZON)).credentialsProvider(ProfileCredentialsProvider.create()).build()) {

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
            throw new ArquivoAmazonException(e.getMessage());
        } catch (Exception e) {
            throw new ArquivoAmazonException("ERRO: Erro ao buscar arquivo no diretório: " + caminhoDiretorio + " da amazon! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public void realizarUploadAmazon(UploadArquivoDTO uploadArquivoDTO) throws ArquivoAmazonException {
        try {
            validarCamposUploadArquivo(uploadArquivoDTO);
            byte[] arquivoBytes = converterUtil.converterBase64EmBytes(uploadArquivoDTO.getArquivoBase64());
            enviarArquivoAmazon(uploadArquivoDTO.getCaminhoDiretorio(), arquivoBytes);
            verificarArquivoEnviado(uploadArquivoDTO.getCaminhoDiretorio());
        } catch (BadRequestException e) {
            throw new ArquivoAmazonException(e.getMessage());
        } catch (ConverterException e) {
            throw new ArquivoAmazonException("ERRO: Erro ao realizar conversão do arquivo: " + uploadArquivoDTO.getNomeArquivo() + "! - MENSAGEM DO ERRO: " + e.getMessage());
        } catch (Exception e) {
            throw new ArquivoAmazonException("ERRO: Erro ao realizar upload do arquivo: " + uploadArquivoDTO.getNomeArquivo() + "! - MENSAGEM DO ERRO: " + e.getMessage());
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
                S3Client s3Client = S3Client.builder().region(Region.of(REGION_AMAZON)).credentialsProvider(ProfileCredentialsProvider.create()).build()) {

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
        try (S3Client s3Client = S3Client.builder().region(Region.of(REGION_AMAZON)).credentialsProvider(ProfileCredentialsProvider.create()).build()) {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();
            s3Client.headObject(headObjectRequest);
        } catch (S3Exception e) {
            throw new Exception("ERRO: Arquivo não encontrado após realização do upload! - MENSAGEM DO ERRO: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("ERRO: Erro ao verificar se o arquivo foi enviado! - MENSAGEM DO ERRO: " + e.getMessage());
        }
    }

    public byte[] baixarArquivo(String caminhoDiretorio) throws ArquivoAmazonException {
        try (S3Client s3Client = S3Client.builder().region(Region.of(REGION_AMAZON)).credentialsProvider(ProfileCredentialsProvider.create()).build()) {
            if (caminhoDiretorio == null || caminhoDiretorio.isBlank()) {
                throw new BadRequestException("Caminho do diretório não encontrado para baixar!");
            }

            // Solicitação de obtenção do objeto
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();

            // Obtém o arquivo como um InputStream
            try (ResponseInputStream<GetObjectResponse> s3ObjectResponse = s3Client.getObject(getObjectRequest)) {
                // Converte o InputStream para byte array
                return s3ObjectResponse.readAllBytes();
            }
        } catch (BadRequestException e) {
            throw new ArquivoAmazonException(e.getMessage());
        } catch (NoSuchKeyException  e) {
            throw new ArquivoAmazonException(STR."ERRO: Nenhum arquivo encontrado no diretório: \{caminhoDiretorio} da amazon! - MENSAGEM DO ERRO: \{e.getMessage()}");
        } catch (Exception e) {
            throw new ArquivoAmazonException(STR."ERRO: Erro ao baixar o arquivo no diretório: \{caminhoDiretorio} da amazon! - MENSAGEM DO ERRO: \{e.getMessage()}");
        }
    }

    public void excluirArquivo(String caminhoDiretorio) throws ArquivoAmazonException {
        try (S3Client s3Client = S3Client.builder().region(Region.of(REGION_AMAZON)).credentialsProvider(ProfileCredentialsProvider.create()).build()) {
            if (caminhoDiretorio == null || caminhoDiretorio.isBlank()) {
                throw new BadRequestException("Caminho do diretório não encontrado para excluir!");
            }

            // Cria a requisição para deletar o arquivo
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCKET_AMAZON).key(caminhoDiretorio).build();

            // Exclui o arquivo do bucket
            s3Client.deleteObject(deleteObjectRequest);
        } catch (BadRequestException e) {
            throw new ArquivoAmazonException(e.getMessage());
        } catch (NoSuchKeyException  e) {
            throw new ArquivoAmazonException(STR."ERRO: Nenhum arquivo encontrado no diretório: \{caminhoDiretorio} da amazon! - MENSAGEM DO ERRO: \{e.getMessage()}");
        } catch (Exception e) {
            throw new ArquivoAmazonException(STR."ERRO: Erro ao excluir o arquivo no diretório: \{caminhoDiretorio} da amazon! - MENSAGEM DO ERRO: \{e.getMessage()}");
        }
    }

}
