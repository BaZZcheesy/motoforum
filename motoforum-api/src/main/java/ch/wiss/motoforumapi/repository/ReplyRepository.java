package ch.wiss.motoforumapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.wiss.motoforumapi.models.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
}
