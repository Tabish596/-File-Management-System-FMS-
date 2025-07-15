package com.ftm.main.filemanager.fileOperations.repository;

import com.ftm.main.filemanager.fileOperations.enums.Status;
import com.ftm.main.filemanager.fileOperations.model.Chunk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChunkRepository extends MongoRepository<Chunk,String> {
    List<Chunk> findByFileIdAndStatus(String fileId, Status status);
    Optional<Chunk> findByFileIdAndIndex(String fileId, Integer id);
}
