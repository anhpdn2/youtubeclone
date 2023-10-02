package com.ducanh.backend.service;

import com.ducanh.backend.dto.VideoDto;
import com.ducanh.backend.model.Video;
import com.ducanh.backend.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final FileService fileService;
    private final VideoRepository videoRepository;

    public VideoDto editVideo(VideoDto videoDto) {
        Video savedVideo = videoRepository.findById(videoDto.getId()).orElseThrow(() -> new IllegalArgumentException("Cannot find video by id: " + videoDto.getId()));
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideoStatus(videoDto.getVideoStatus());
        videoRepository.save(savedVideo);
        return videoDto;
    }


}
