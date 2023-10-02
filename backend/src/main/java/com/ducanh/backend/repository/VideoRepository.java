package com.ducanh.backend.repository;

import com.ducanh.backend.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video, String> {
}
