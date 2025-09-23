package com.diogo.streaming.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "video", uniqueConstraints = @UniqueConstraint(columnNames = "titulo"))
public class Video {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(length = 1000)
    private String descricao;

    @Column(nullable = false)
    private Integer duracao; // em minutos

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Video() {}
    public Video(String titulo, String descricao, Integer duracao, Categoria categoria) {
        this.titulo = titulo; this.descricao = descricao; this.duracao = duracao; this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getDuracao() { return duracao; }
    public void setDuracao(Integer duracao) { this.duracao = duracao; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}


