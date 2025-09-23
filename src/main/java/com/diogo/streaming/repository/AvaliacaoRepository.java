package com.diogo.streaming.repository;

import com.diogo.streaming.domain.Avaliacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    @Query("""
           select a.video as video, avg(a.nota) as media, count(a) as votos
           from Avaliacao a
           group by a.video
           order by media desc
           """)
    List<VideoRating> topRated(Pageable page);
}
