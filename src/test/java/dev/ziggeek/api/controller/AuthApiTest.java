package dev.ziggeek.api.controller;


import dev.ziggeek.api.MeshGroupApplicationTests;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.RestAssured.given;

@AutoConfigureMockMvc
//@WebMvcTest(controllers = AuthService.class)
public class AuthApiTest extends MeshGroupApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(new HandlerController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void loginAngGetToken() throws Exception {
//        var reqt = MockMvcRequestBuilders.post(
//                        URI.create("http://localhost:8080/api/auth/login"))
//                .accept(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "  \"email\": \"abubakar@gmail.com\",\n" +
//                        "  \"password\": \"kukuruza\"\n" +
//                        "}");
//
//        MvcResult mvcResult = mockMvc.perform(reqt)
//                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//                .andReturn();
//        String resp = mvcResult.getResponse().getContentType();
//        Assertions.assertNotNull(resp);
    }


    @Test
    public void auth() {

        RestAssured.baseURI = "http://localhost:8080/api/auth/login";
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("abubakar@gmail.com");
        authScheme.setPassword("kukuruza");
        RestAssured.authentication = authScheme;

        given().header("Content-Type","application/json")
                .body("{\n" +
                        "   \"email\": \"abubakar@gmail.com\",\n" +
                        "   \"password\": \"kukuruza\"\n" +
                        "}")

                .then().log().status().statusCode(201);
    }
}
