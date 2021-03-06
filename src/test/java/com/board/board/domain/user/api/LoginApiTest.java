package com.board.board.domain.user.api;

import com.board.board.domain.user.dto.LoginRequest;
import com.board.board.domain.user.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginApiTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void testLogin() throws Exception {

        LoginRequest request = new LoginRequest("??????", "1234");

        String content = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post("/login")
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth_login",
                        requestFields(
                                fieldWithPath("userId").description("?????????"),
                                fieldWithPath("password").description("?????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("status").description("?????? ??????"),
                                fieldWithPath("data.token").description("?????? ??????"),
                                fieldWithPath("data.user.id").description("??????  PK"),
                                fieldWithPath("data.user.userId").description("?????? ?????????"),
                                fieldWithPath("data.user.password").description("????????????"),
                                fieldWithPath("data.user.userName").description("?????? ??????"),
                                fieldWithPath("data.user.teamName").description("??? ??????"),
                                fieldWithPath("data.user.userInfo").description("?????? ??????"),
                                fieldWithPath("data.user.age").description("?????? ??????"),
                                fieldWithPath("data.user.phoneNum").description("?????? ??????")
                        )));
    }
}