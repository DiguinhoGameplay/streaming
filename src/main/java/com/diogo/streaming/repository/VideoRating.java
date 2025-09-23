package com.diogo.streaming.repository;

import com.diogo.streaming.domain.Video;



public interface VideoRating {
    Video getVideo();
    Double getAvg();
    Long getQtd();
    Double getMedia();
    Long getVotos();
}
