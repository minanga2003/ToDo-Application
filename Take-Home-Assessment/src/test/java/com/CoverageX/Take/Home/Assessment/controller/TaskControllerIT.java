package com.CoverageX.Take.Home.Assessment.controller;


import com.CoverageX.Take.Home.Assessment.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                // ← add this line
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
        })
class TaskControllerIT {

    @Autowired
    TestRestTemplate rest;

    @Test
    void fullCrudFlow() {
        // create
        TaskDTO req = new TaskDTO(null, "IT Task", "desc", false);
        ResponseEntity<TaskDTO> post = rest.postForEntity("/api/tasks", req, TaskDTO.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long id = post.getBody().getId();

        // list
        ResponseEntity<List<TaskDTO>> get = rest.exchange(
                "/api/tasks?limit=5",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        assertThat(get.getBody()).extracting(TaskDTO::getId).contains(id);

        // complete
        rest.put("/api/tasks/{id}/complete", null, id);

        // after complete, it should no longer appear
        ResponseEntity<List<TaskDTO>> getAfter = rest.exchange(
                "/api/tasks?limit=5",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {});
        assertThat(getAfter.getBody()).extracting(TaskDTO::getId).doesNotContain(id);
    }
}
