package com.ftm.main.filemanager.userManagement.repository;

import com.ftm.main.filemanager.userManagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

}
