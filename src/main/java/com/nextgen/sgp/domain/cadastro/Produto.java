package com.nextgen.sgp.domain.cadastro;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @ManyToOne
    @JoinColumn(name = "CODTIPPRO", referencedColumnName = "CODTIPPRO")
    private TipoProduto tipoProduto;

    @ManyToOne
    @JoinColumn(name = "CODSTAPRO", referencedColumnName = "CODSTAPRO")
    private StatusProduto statusProduto;

    @ManyToOne
    @JoinColumn(name = "CODMRC", referencedColumnName = "CODMRC")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "CODDPT", referencedColumnName = "CODDPT")
    private Departamento departamento;

    @ManyToOne
    @JoinColumn(name = "CODCTG", referencedColumnName = "CODCTG")
    private Categoria categoria;

    @Column(name = "DATCAD")
    private LocalDateTime dataCadastro;

    @Column(name = "DATULTALT")
    private LocalDateTime dataUltimaAlteracao;

    @Column(name = "PRCPRO")
    private BigDecimal preco;

    @Column(name = "QNTETQ")
    private Integer quantidadeEstoque;

    @Column(name = "DESDET")
    private String descricaoDetalhada;

    public Produto() {

    }

    public Produto(Long codigo, String nome, String codigoBarrasEAN, TipoProduto tipoProduto, StatusProduto statusProduto, Marca marca, Departamento departamento, Categoria categoria, LocalDateTime dataCadastro, LocalDateTime dataUltimaAlteracao, BigDecimal preco, Integer quantidadeEstoque, String descricaoDetalhada) {
        this.codigo = codigo;
        this.nome = nome;
        this.codigoBarrasEAN = codigoBarrasEAN;
        this.tipoProduto = tipoProduto;
        this.statusProduto = statusProduto;
        this.marca = marca;
        this.departamento = departamento;
        this.categoria = categoria;
        this.dataCadastro = dataCadastro;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.descricaoDetalhada = descricaoDetalhada;
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

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public StatusProduto getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(StatusProduto statusProduto) {
        this.statusProduto = statusProduto;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
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

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

}
