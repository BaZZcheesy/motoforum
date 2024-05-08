package ch.wiss.motoforumapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.wiss.motoforumapi.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
