package org.example.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

    }

    @Test
    void testCreateClientSuccess() throws Exception {
        ClientRequestDto clientRequestDto = ClientRequestDto.builder()
                .firstName("Roma").build();
        ClientResponseDto clientResponseDto = ClientResponseDto.builder()
                .firstName("Roma")
                .build();
        when(clientService.createClient(clientRequestDto)).thenReturn(clientResponseDto);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Roma")));

        verify(clientService).createClient(clientRequestDto);
    }




}
