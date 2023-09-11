package com.keystoneconstructs.credentia.repository;

import com.keystoneconstructs.credentia.pojo.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {
}
