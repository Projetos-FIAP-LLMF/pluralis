package com.pluralis.pluralis.repository;

import com.pluralis.pluralis.model.AnonymousFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousFeedbackRepository extends JpaRepository<AnonymousFeedback, Long> {
}
