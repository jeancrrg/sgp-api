package com.nextgen.sgp.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "TPRODUTO")
public class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODPRO")
    private Long codigo;

    @Column(name = "NOMPRO")
    private String nome;

    @Column(name = "CODBAREAN")
    private String codigoBarrasEAN;

    @Column(name = "CODTIPPRO")
    private Integer codigoTipoProduto;

    @Column(name = "CODSTA")
    private Long codigoStatus;

    @ManyToOne
    @JoinColumn(name = "CODMRC", referencedColumnName = "CODMRC")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "CODDPT", referencedColumnName = "CODDPT")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "CODCTG", referencedColumnName = "CODCTG")
    private Categoria categoria;

    @Column(name = "DATULTALT")
    private Date dataUltimaAlteracao;

    @Column(name = "PRCPRO")
    private BigDecimal preco;

    @Column(name = "QNTETQ")
    private Integer quantidadeEstoque;

    public Produto() {

    }

    public Produto(Long codigo, String nome, String codigoBarrasEAN, Integer codigoTipoProduto, Long codigoStatus, Marca marca, Departamento departamento, Categoria categoria, Date dataUltimaAlteracao, BigDecimal preco, Integer quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.codigoBarrasEAN = codigoBarrasEAN;
        this.codigoTipoProduto = codigoTipoProduto;
        this.codigoStatus = codigoStatus;
        this.marca = marca;
        this.departamento = departamento;
        this.categoria = categoria;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
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

    public String getCodigoBarrasEAN() {
        return codigoBarrasEAN;
    }

    public void setCodigoBarrasEAN(String codigoBarrasEAN) {
        this.codigoBarrasEAN = codigoBarrasEAN;
    }

    public Integer getCodigoTipoProduto() {
        return codigoTipoProduto;
    }

    public void setCodigoTipoProduto(Integer codigoTipoProduto) {
        this.codigoTipoProduto = codigoTipoProduto;
    }

    public Long getCodigoStatus() {
        return codigoStatus;
    }

    public void setCodigoStatus(Long codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }


}
