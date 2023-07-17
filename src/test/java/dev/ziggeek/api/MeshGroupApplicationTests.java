package dev.ziggeek.api;

import dev.ziggeek.api.initializer.Postgres;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {Postgres.Initializer.class})
@Transactional
public class MeshGroupApplicationTests {

    @BeforeAll
    static void init() {
        Postgres.container.start();
    }

    @AfterAll
    static void afterAll() {
        Postgres.container.close();
    }
}
