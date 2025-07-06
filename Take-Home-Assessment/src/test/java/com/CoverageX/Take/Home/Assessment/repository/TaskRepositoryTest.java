package com.CoverageX.Take.Home.Assessment.repository;

import com.CoverageX.Take.Home.Assessment.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(properties = {
        // point Spring Boot at an in-memory H2 URL
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        // Hibernate DDL and dialect
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class TaskRepositoryTest {

    @Autowired
    TaskRepository repo;

    @Test
    void findByCompletedFalseOrderByCreatedAtDesc_filtersAndSorts() {
        // given
        Task done = new Task("A","x"); done.setCompleted(true);
        Task t1   = new Task("B","y"); t1.setCreatedAt(LocalDateTime.now().minusDays(1));
        Task t2   = new Task("C","z"); t2.setCreatedAt(LocalDateTime.now());
        repo.saveAll(List.of(done, t1, t2));

        // when
        List<Task> list = repo.findByCompletedFalseOrderByCreatedAtDesc(PageRequest.of(0,5));

        // then
        assertThat(list).extracting(Task::getTitle).containsExactly("C","B");
    }
}
