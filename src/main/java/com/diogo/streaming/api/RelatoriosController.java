package com.diogo.streaming.api;

import com.diogo.streaming.domain.Video;
import com.diogo.streaming.repository.UsuarioRepository;
import com.diogo.streaming.repository.VideoRepository;
import com.diogo.streaming.repository.VideoRating;
import com.diogo.streaming.repository.VideoViews;
import com.diogo.streaming.repository.UserViews;

import org.springframework.web.bind.annotation.*;



import java.util.*;


@RestController
@RequestMapping("/api")
public class RelatoriosController {

    private final VideoRepository videoRepo;
    private final UsuarioRepository usuarioRepo;

    public RelatoriosController(VideoRepository videoRepo, UsuarioRepository usuarioRepo) {
        this.videoRepo = videoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // 1) Vídeos por título (ordenados)
    @GetMapping("/videos")
    public List<Video> videosPorTitulo(@RequestParam String titulo) {
        return videoRepo.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
    }

    // 2) Vídeos por categoria (ordenados)
    @GetMapping("/categorias/{id}/videos")
    public List<Video> videosDaCategoria(@PathVariable Long id) {
        return videoRepo.findByCategoriaIdOrderByTituloAsc(id);
    }

    // 3) Top N mais bem avaliados
    @GetMapping("/videos/top-rated")
    public List<Map<String,Object>> topAvaliados(@RequestParam(defaultValue = "10") int limit) {
        List<VideoRating> data = videoRepo.topMaisBemAvaliados(limit);
        List<Map<String,Object>> out = new ArrayList<>();
        for (VideoRating vr : data) {
            Map<String,Object> row = new LinkedHashMap<>();
            row.put("videoId", vr.getVideo().getId());
            row.put("titulo",  vr.getVideo().getTitulo());
            row.put("media",   vr.getAvg());
            row.put("votos",   vr.getQtd());
            out.add(row);
        }
        return out;
    }

    // 4) Top N mais assistidos
    @GetMapping("/videos/top-viewed")
    public List<Map<String,Object>> topAssistidos(@RequestParam(defaultValue = "10") int limit) {
        List<VideoViews> data = videoRepo.topMaisAssistidos(limit);
        List<Map<String,Object>> out = new ArrayList<>();
        for (VideoViews vw : data) {
            Map<String,Object> row = new LinkedHashMap<>();
            row.put("videoId", vw.getVideo().getId());
            row.put("titulo",  vw.getVideo().getTitulo());
            row.put("views",   vw.getTotal());
            out.add(row);
        }
        return out;
    }

    // 5) Usuário(s) que mais assistiu(aram) vídeos distintos
    @GetMapping("/usuarios/mais-assistiu")
    public List<Map<String,Object>> usuariosQueMaisAssistiram(@RequestParam(defaultValue = "1") int limit) {
        List<UserViews> data = usuarioRepo.usuariosQueMaisAssistiram(limit);
        List<Map<String,Object>> out = new ArrayList<>();
        for (UserViews uv : data) {
            Map<String,Object> row = new LinkedHashMap<>();
            row.put("usuarioId",       uv.getUsuario().getId());
            row.put("nome",            uv.getUsuario().getNome());
            row.put("videosDistintos", uv.getTotal());
            out.add(row);
        }
        return out;


    }
}
