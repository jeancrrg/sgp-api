package com.nextgen.sgp.domain.cadastro;

import com.nextgen.sgp.domain.enums.Status;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Jean Garcia
 */
@Entity
@Table(name = "TIMGPRODUTO")
public class ImagemProduto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final long KILOBYTE = 1024;
    private final long MEGABYTE = KILOBYTE * 1024;
    private final long GIGABYTE = MEGABYTE * 1024;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIMG")
    private Long codigo;

    @Column(name = "NOMIMG")
    private String nome;

    @Column(name = "NOMIMGSVD")
    private String nomeImagemServidor;

    @Column(name = "TAMIMG")
    private Long tamanhoImagemBytes;

    @Column(name = "CODPRO")
    private Long codigoProduto;

    @Column(name = "TIPEXTIMG")
    private String tipoExtensaoImagem;

    @Column(name = "DATULTALT")
    private LocalDateTime dataUltimaAlteracao;

    @Transient
    private String arquivoBase64;

    @Transient
    private String urlImagem;

    @Transient
    private String tamanhoImagemConvertido;

    @Transient
    private Status statusCadastro;

    @Transient
    private String mensagemErroCadastro;

    public ImagemProduto() {

    }

    public ImagemProduto(Long codigo, String nome, String nomeImagemServidor, Long tamanhoImagemBytes, Long codigoProduto, String tipoExtensaoImagem, LocalDateTime dataUltimaAlteracao, String arquivoBase64, String urlImagem, String tamanhoImagemConvertido, Status statusCadastro, String mensagemErroCadastro) {
        this.codigo = codigo;
        this.nome = nome;
        this.nomeImagemServidor = nomeImagemServidor;
        this.tamanhoImagemBytes = tamanhoImagemBytes;
        this.codigoProduto = codigoProduto;
        this.tipoExtensaoImagem = tipoExtensaoImagem;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.arquivoBase64 = arquivoBase64;
        this.urlImagem = urlImagem;
        this.tamanhoImagemConvertido = tamanhoImagemConvertido;
        this.statusCadastro = statusCadastro;
        this.mensagemErroCadastro = mensagemErroCadastro;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeImagemServidor() {
        return nomeImagemServidor;
    }

    public void setNomeImagemServidor(String nomeImagemServidor) {
        this.nomeImagemServidor = nomeImagemServidor;
    }

    public Long getTamanhoImagemBytes() {
        return tamanhoImagemBytes;
    }

    public void setTamanhoImagemBytes(Long tamanhoImagemBytes) {
        this.tamanhoImagemBytes = tamanhoImagemBytes;
    }

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getTipoExtensaoImagem() {
        return tipoExtensaoImagem;
    }

    public void setTipoExtensaoImagem(String tipoExtensaoImagem) {
        this.tipoExtensaoImagem = tipoExtensaoImagem;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public String getArquivoBase64() {
        return arquivoBase64;
    }

    public void setArquivoBase64(String arquivoBase64) {
        this.arquivoBase64 = arquivoBase64;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getTamanhoImagemConvertido() {
        this.tamanhoImagemConvertido = null;
        if (this.tamanhoImagemBytes >= GIGABYTE) {
            this.tamanhoImagemConvertido = String.format("%d GB", (long) Math.ceil((double) this.tamanhoImagemBytes / GIGABYTE));
        } else if (this.tamanhoImagemBytes >= MEGABYTE) {
            this.tamanhoImagemConvertido = String.format("%d MB", (long) Math.ceil((double) this.tamanhoImagemBytes / MEGABYTE));
        } else if (this.tamanhoImagemBytes >= KILOBYTE) {
            this.tamanhoImagemConvertido = String.format("%d KB", (long) Math.ceil((double) this.tamanhoImagemBytes / KILOBYTE));
        } else {
            this.tamanhoImagemConvertido = String.format("%d bytes", this.tamanhoImagemBytes);
        }
        return this.tamanhoImagemConvertido;
    }

    public void setTamanhoImagemConvertido(String tamanhoImagemConvertido) {
        this.tamanhoImagemConvertido = tamanhoImagemConvertido;
    }

    public Status getStatusCadastro() {
        return statusCadastro;
    }

    public void setStatusCadastro(Status statusCadastro) {
        this.statusCadastro = statusCadastro;
    }

    public String getMensagemErroCadastro() {
        return mensagemErroCadastro;
    }

    public void setMensagemErroCadastro(String mensagemErroCadastro) {
        this.mensagemErroCadastro = mensagemErroCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagemProduto imagem = (ImagemProduto) o;
        return Objects.equals(codigo, imagem.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
