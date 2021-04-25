package com.exadel.fedorov.orders.controllers;

import com.exadel.fedorov.orders.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = Application.class)
class ClientControllerDocumentationTest {

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void getTest() throws Exception {

        this.mockMvc.perform(get("/rest/clients/{id}", 1).contentType(MediaTypes.HAL_JSON)
        ).andExpect(status().isOk())
                .andDo(document("crud-get-example",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));

                /*
                        requestFields(//fieldWithPath("id").description("The id of the input" +
                                // collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),

    */
    }

    @Test
    void createTest() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("name", "Bob Johnson");
        crud.put("login", "NewBobJJ");
        crud.put("email", "emmmNew@gggggg.com");
        crud.put("phone", "123333333333333");
        crud.put("address", "Example street 100");

        this.mockMvc.perform(post("/rest/clients").contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isCreated())
                .andDo(document("create-crud-test",
                        requestFields(
                                fieldWithPath("name").description("The name of the input client."),
                                fieldWithPath("login").description("The login of the input client."),
                                fieldWithPath("email").description("The email of the input client."),
                                fieldWithPath("phone").description("The phone of the input client."),
                                fieldWithPath("address").description("The address of the input client.")
                        )));
    }

    @Test
    void updateTest() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("name", "Bob Johnson");
        crud.put("login", "NewBobJJ");
        crud.put("email", "emmmNew@gggggg.com");
        crud.put("phone", "123333333333333");
        crud.put("address", "Example street 100");

        this.mockMvc.perform(put("/rest/clients/{id}", 2).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isOk())
                .andDo(document("update-crud-test",
                        requestFields(fieldWithPath("name").description("The name of the input client."),
                                fieldWithPath("login").description("The login of the input client."),
                                fieldWithPath("email").description("The email of the input client."),
                                fieldWithPath("phone").description("The phone of the input client."),
                                fieldWithPath("address").description("The address of the input client.")
                        )));
    }

    @Test
    void updateWithInvalidIdTest() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("name", "Bob Johnson");
        crud.put("login", "NewBobJJ");
        crud.put("email", "emmmNew@gggggg.com");
        crud.put("phone", "123333333333333");
        crud.put("address", "Example street 100");

        this.mockMvc.perform(put("/rest/clients/{id}", -5).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().is(400))
                .andDo(document("update-crud-with-zero-id-test",
                        requestFields(fieldWithPath("name").description("The name of the input client."),
                                fieldWithPath("login").description("The login of the input client."),
                                fieldWithPath("email").description("The email of the input client."),
                                fieldWithPath("phone").description("The phone of the input client."),
                                fieldWithPath("address").description("The address of the input client.")
                        )));
    }

    @Test
    void updateWithInvalidIdTest2() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("name", "Bob Johnson");
        crud.put("login", "NewBobJJ");
        crud.put("email", "emmmNew@gggggg.com");
        crud.put("phone", "123333333333333");
        crud.put("address", "Example street 100");

        this.mockMvc.perform(put("/rest/clients/{id}", 0).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().is(400))
                .andDo(document("update-crud-with-negative-id-test",
                        requestFields(fieldWithPath("name").description("The name of the input client."),
                                fieldWithPath("login").description("The login of the input client."),
                                fieldWithPath("email").description("The email of the input client."),
                                fieldWithPath("phone").description("The phone of the input client."),
                                fieldWithPath("address").description("The address of the input client.")
                        )));
    }

    @Test
    void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/rest/clients/{id}", 6))
                .andExpect(status().is(204))
                .andDo(document("crud-delete-test", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }

    @Test
    void deleteWithInvalidIdTest() throws Exception {
        this.mockMvc.perform(delete("/rest/clients/{id}", 0))
                .andExpect(status().is(400))
                .andDo(document("crud-delete-with-zero-id", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }

    @Test
    void deleteWithInvalidIdTest2() throws Exception {
        this.mockMvc.perform(delete("/rest/clients/{id}", -5))
                .andExpect(status().is(400))
                .andDo(document("crud-delete-with-negative-id", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }

}