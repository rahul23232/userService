package com.backend.userservice.repositories;

import com.backend.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session save(Session session);

}
