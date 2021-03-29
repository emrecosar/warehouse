package com.emrecosar.warehouse.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseIntegrationTest {

    protected final String API_PRODUCT_BASE = "/products";

    protected final String API_ARTICLE_BASE = "/articles";

    @Autowired
    protected TestRestTemplate testRestTemplate;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Value("${user.username}")
    protected String userUsername;
    @Value("${user.password}")
    protected String userPassword;
    @Value("${admin.username}")
    protected String adminUserName;
    @Value("${admin.password}")
    protected String adminPassword;

    public HttpHeaders getUserDefaultHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(userUsername, userPassword);
        return httpHeaders;
    }

    public HttpHeaders getAdminDefaultHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth(adminUserName, adminPassword);
        return httpHeaders;
    }



}
