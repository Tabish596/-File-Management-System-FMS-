package com.ftm.main.filemanager.fileOperations.repository;

import com.ftm.main.filemanager.fileOperations.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends MongoRepository<File,String> {
    Optional<File> findByOwnerIdAndName(String ownerId, String name);
}
