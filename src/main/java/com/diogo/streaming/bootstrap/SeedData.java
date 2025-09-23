package com.diogo.streaming.bootstrap;

import com.diogo.streaming.domain.*;
import com.diogo.streaming.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    private final UsuarioRepository usuarioRepo;
    private final PerfilRepository perfilRepo;
    private final CategoriaRepository categoriaRepo;
    private final VideoRepository videoRepo;
    private final AvaliacaoRepository avaliacaoRepo;
    private final VisualizacaoRepository visualizacaoRepo;

    public SeedData(UsuarioRepository usuarioRepo, PerfilRepository perfilRepo,
                    CategoriaRepository categoriaRepo, VideoRepository videoRepo,
                    AvaliacaoRepository avaliacaoRepo, VisualizacaoRepository visualizacaoRepo) {
        this.usuarioRepo = usuarioRepo;
        this.perfilRepo = perfilRepo;
        this.categoriaRepo = categoriaRepo;
        this.videoRepo = videoRepo;
        this.avaliacaoRepo = avaliacaoRepo;
        this.visualizacaoRepo = visualizacaoRepo;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepo.count() > 0) return; // já populado, evita duplicar

        // Usuários e perfis
        Usuario u1 = new Usuario(); u1.setNome("Ana");   u1.setEmail("ana@ex.com");   u1.setSenha("123"); u1.setDataCadastro(LocalDateTime.now());
        Usuario u2 = new Usuario(); u2.setNome("Bruno"); u2.setEmail("bruno@ex.com"); u2.setSenha("123"); u2.setDataCadastro(LocalDateTime.now());
        usuarioRepo.saveAll(List.of(u1, u2));

        Perfil p1 = new Perfil("Ana Kids", u1);
        Perfil p2 = new Perfil("Ana Adulto", u1);
        Perfil p3 = new Perfil("Bruno", u2);
        perfilRepo.saveAll(List.of(p1, p2, p3));

        // Categorias e vídeos
        Categoria acao = new Categoria("Ação");
        Categoria doc  = new Categoria("Documentário");
        categoriaRepo.saveAll(List.of(acao, doc));

        Video v1 = new Video("Missão Impossível", "Espionagem", 130, acao);
        Video v2 = new Video("Missão Marte", "Sci-fi", 110, acao);
        Video v3 = new Video("Vida Selvagem", "Natureza", 50, doc);
        videoRepo.saveAll(List.of(v1, v2, v3));

        // Visualizações
        visualizacaoRepo.saveAll(List.of(
                new Visualizacao(p1, v1, LocalDateTime.now(), 100),
                new Visualizacao(p1, v2, LocalDateTime.now(), 90),
                new Visualizacao(p2, v1, LocalDateTime.now(), 60),
                new Visualizacao(p3, v1, LocalDateTime.now(), 100),
                new Visualizacao(p3, v3, LocalDateTime.now(), 100)
        ));

        // Avaliações
        avaliacaoRepo.saveAll(List.of(
                new Avaliacao(p1, v1, 10, "Top!"),
                new Avaliacao(p2, v1, 8,  "Bom"),
                new Avaliacao(p3, v2, 7,  "Ok"),
                new Avaliacao(p3, v3, 9,  "Lindo")
        ));

        // ===== Relatórios =====
        System.out.println("\n> 1) Vídeos com 'Missão' (ordenados):");
        videoRepo.findByTituloContainingIgnoreCaseOrderByTituloAsc("missão")
                .forEach(v -> System.out.println("- " + v.getTitulo()));

        System.out.println("\n> 2) Vídeos da categoria Ação (ordenados):");
        videoRepo.findByCategoriaIdOrderByTituloAsc(acao.getId())
                .forEach(v -> System.out.println("- " + v.getTitulo()));

        System.out.println("\n> 3) Top 10 mais bem avaliados:");
        avaliacaoRepo.topRated(PageRequest.of(0, 10))
                .forEach(r -> System.out.printf("- %s (média %.2f, votos %d)%n",
                        r.getVideo().getTitulo(), r.getMedia(), r.getVotos()));

        System.out.println("\n> 4) Top 10 mais assistidos:");
        visualizacaoRepo.topWatched(PageRequest.of(0, 10))
                .forEach(vw -> System.out.printf("- %s (views %d)%n",
                        vw.getVideo().getTitulo(), vw.getTotal()));

        System.out.println("\n> 5) Usuário que mais assistiu (distinct):");
        visualizacaoRepo.topUsersDistinct(PageRequest.of(0, 1))
                .forEach(u -> System.out.printf("- %s (%d vídeos)%n",
                        u.getUsuario().getNome(), u.getTotal()));
    }
}
