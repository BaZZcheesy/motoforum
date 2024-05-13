package ch.wiss.motoforumapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.wiss.motoforumapi.models.Question;

// Repository um für die Questions
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
