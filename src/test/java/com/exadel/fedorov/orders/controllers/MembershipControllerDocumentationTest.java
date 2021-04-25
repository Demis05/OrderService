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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
class MembershipControllerDocumentationTest {

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

        this.mockMvc.perform(get("/rest/memberships/{id}", 3).contentType(MediaTypes.HAL_JSON)
        ).andExpect(status().isOk())
                .andDo(document("crud-get-example",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void createTest() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("clientId", 1L);
        crud.put("title", "standart discount");
        crud.put("validity", "2 mons 1 days");
        crud.put("startDate", "2021-06-10 00:00");
        crud.put("endDate", "2021-07-10 00:00");
        crud.put("discount", 30);

        this.mockMvc.perform(post("/rest/memberships").contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().isCreated())
                .andDo(document("create-crud-test",
                        requestFields(
                                fieldWithPath("clientId").description("The client id of the input membership."),
                                fieldWithPath("title").description("The title of the input membership."),
                                fieldWithPath("validity").description("The validity of the input membership."),
                                fieldWithPath("startDate").description("The start date of the input membership."),
                                fieldWithPath("endDate").description("The end date of the input membership."),
                                fieldWithPath("discount").description("The discount of the input membership.")
                        )));
    }

    @Test
    void updateTest() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime end = LocalDateTime.parse("2021-07-10 00:00", formatter);

        this.mockMvc.perform(put("/rest/memberships/{id}", 2).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(end)))
                .andExpect(status().isOk())
                .andDo(document("update-crud-test",
                        requestFields(
                                fieldWithPath("clientId").description("The client id of the input membership."),
                                fieldWithPath("title").description("The title of the input membership."),
                                fieldWithPath("validity").description("The validity of the input membership."),
                                fieldWithPath("startDate").description("The start date of the input membership."),
                                fieldWithPath("endDate").description("The end date of the input membership."),
                                fieldWithPath("discount").description("The discount of the input membership.")
                        )));
    }

    @Test
    void updateWithInvalidIdTest() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("endDate", "endDate");

        this.mockMvc.perform(put("/rest/memberships/{id}", -5).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().is(400))
                .andDo(document("update-crud-with-zero-id-test",
                        requestFields(
                                fieldWithPath("endDate").description("The new end date of the input membership.")
                        )));
    }

    @Test
    void updateWithInvalidIdTest2() throws Exception {
        Map<String, Object> crud = new HashMap<>();
        crud.put("endDate", "endDate");

        this.mockMvc.perform(put("/rest/memberships/{id}", 0).contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(crud)))
                .andExpect(status().is(400))
                .andDo(document("update-crud-with-negative-id-test",
                        requestFields(
                                fieldWithPath("endDate").description("The end date of the input membership.")
                        )));
    }

    @Test
    void deleteTest() throws Exception {
        this.mockMvc.perform(delete("/rest/memberships/{id}", 60))
                .andExpect(status().is(204))
                .andDo(document("crud-delete-test", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }

    @Test
    void deleteWithInvalidIdTest() throws Exception {
        this.mockMvc.perform(delete("/rest/memberships/{id}", 0))
                .andExpect(status().is(400))
                .andDo(document("crud-delete-with-zero-id", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }

    @Test
    void deleteWithInvalidIdTest2() throws Exception {
        this.mockMvc.perform(delete("/rest/memberships/{id}", -5))
                .andExpect(status().is(400))
                .andDo(document("crud-delete-with-negative-id", pathParameters(parameterWithName("id").description("The id of the input to delete"))));
    }
}