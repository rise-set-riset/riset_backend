package com.github.riset_backend.chating.repository;

import com.github.riset_backend.chating.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}
