package com.CoverageX.Take.Home.Assessment.repository;


import com.CoverageX.Take.Home.Assessment.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletedFalseOrderByCreatedAtDesc(Pageable pageable);
}
