package pl.pjwstk.woloappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!checkIfDatabaseExists()) {
            createDatabase();
            createUser();
        }


    }

    private boolean checkIfDatabaseExists() {
        String query = "SELECT 1 FROM pg_database WHERE datname = 'postgres'";
        Integer result = jdbcTemplate.queryForObject(query, Integer.class);
        return result != null && result == 1;
    }

    private void createDatabase() {
        String createDatabaseQuery = "CREATE DATABASE postgres";
        jdbcTemplate.execute(createDatabaseQuery);
    }

    private void createUser() {
        String createUserQuery = "CREATE USER postgres WITH PASSWORD Qwe123 SUPERUSER";
        jdbcTemplate.execute(createUserQuery);
    }
}