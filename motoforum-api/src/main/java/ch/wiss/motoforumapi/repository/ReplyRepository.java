package ch.wiss.motoforumapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.wiss.motoforumapi.models.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
