package org.teamseven.hms.backend.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.teamseven.hms.backend.sample.controller.SampleController;
import org.teamseven.hms.backend.sample.dto.SampleResponse;
import org.teamseven.hms.backend.sample.service.SampleService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class SampleControllerTest {
    @Mock
    SampleService sampleService;

    @InjectMocks
    SampleController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetSampleResponse() throws Exception {
        SampleResponse response = new SampleResponse(42, "random text");

        ResponseWrapper.Success<SampleResponse> expectedResponse = new ResponseWrapper.Success<>(response);
        when(sampleService.getSampleResponse())
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/samples"))
                .andExpect(status().isOk())
                        .andExpect(content().json(convertObjectToJsonString(expectedResponse)));
    }

    private String convertObjectToJsonString(
            ResponseWrapper.Success<SampleResponse> expectedResponse
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(expectedResponse);
    }
}
