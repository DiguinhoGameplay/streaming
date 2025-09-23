package com.diogo.streaming.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visualizacao")
public class Visualizacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @ManyToOne(optional = false) @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private Integer progresso; // 0..100 (%)

    public Visualizacao() {}
    public Visualizacao(Perfil perfil, Video video, LocalDateTime dataHora, Integer progresso) {
        this.perfil = perfil; this.video = video; this.dataHora = dataHora; this.progresso = progresso;
    }

    public Long getId() { return id; }
    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
    public Video getVideo() { return video; }
    public void setVideo(Video video) { this.video = video; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public Integer getProgresso() { return progresso; }
    public void setProgresso(Integer progresso) { this.progresso = progresso; }
}
