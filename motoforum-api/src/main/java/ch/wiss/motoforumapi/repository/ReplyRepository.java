package ch.wiss.motoforumapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.wiss.motoforumapi.models.Question;
import ch.wiss.motoforumapi.models.Reply;

// Repository um für die Replies
@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<List<Reply>> findByQuestion(Question question);
}
