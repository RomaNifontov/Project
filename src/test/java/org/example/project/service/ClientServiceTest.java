package org.example.project.service;

import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.dto.EmailRequestDto;
import org.example.project.dto.PhoneRequestDto;
import org.example.project.mapper.ClientMapperImpl;
import org.example.project.model.Client;
import org.example.project.model.Email;
import org.example.project.model.PhoneNumber;
import org.example.project.repository.ClientRepository;
import org.example.project.validator.ClientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Captor
    private ArgumentCaptor<Client> clientCaptor;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientValidator clientValidator;
    @Spy
    private ClientMapperImpl clientMapper;

    private ClientRequestDto requestDto;
    private EmailRequestDto emailRequestDto;
    private PhoneRequestDto phoneRequestDto;
    private Client client;

    @BeforeEach
    void setUp() {
        requestDto = ClientRequestDto.builder()
                .firstName("Roma")
                .build();
        emailRequestDto = EmailRequestDto.builder()
                .clientId(13L)
                .email("roma@example.com")
                .build();
        client = Client.builder()
                .id(13L)
                .firstName("Roma")
                .emails(new HashSet<>())
                .phoneNumbers(new HashSet<>())
                .build();
        phoneRequestDto = PhoneRequestDto.builder()
                .phoneNumber("+7 9111111111")
                .clientId(13L)
                .build();

    }

    @Test
    void testCreateClientSuccess() {
        ClientResponseDto responseDto = clientService.createClient(requestDto);
        verify(clientRepository).save(clientCaptor.capture());
        assertEquals("Roma", clientCaptor.getValue().getFirstName());
        assertEquals("Roma", responseDto.getFirstName());
        assertEquals(0, responseDto.getEmails().size());
    }

    @Test
    void testAddNewEmailSuccess() {
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        ClientResponseDto responseDto = clientService.addNewEmail(emailRequestDto);
        verify(clientRepository).save(client);
        assertEquals("Roma", responseDto.getFirstName());
        assertEquals("roma@example.com", responseDto.getEmails().get(0));
    }

    @Test
    void testAddNewPhoneSuccess() {
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        ClientResponseDto responseDto = clientService.addNewTelephoneNumber(phoneRequestDto);
        verify(clientRepository).save(client);
        assertEquals("Roma", responseDto.getFirstName());
        assertEquals("+7 9111111111", responseDto.getPhoneNumbers().get(0));
    }

    @Test
    void testGetAllClientsSuccess() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        List<ClientResponseDto> clients = clientService.getAllClients();
        verify(clientRepository).findAll();
        assertEquals(1, clients.size());
        assertEquals("Roma", clients.get(0).getFirstName());
    }

    @Test
    void testGetClientByIdSuccess() {
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        ClientResponseDto responseDto = clientService.getClientById(13L);
        verify(clientRepository).findById(13L);
        assertEquals("Roma", responseDto.getFirstName());
    }

    @Test
    void testGetPhoneNumbersByClientIdSuccess() {
        PhoneNumber number = PhoneNumber.builder().number("+7 9111111111").build();
        client.getPhoneNumbers().add(number);
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        List<String> phoneNumbers = clientService.getPhoneNumbersByClientId(13L);
        verify(clientRepository).findById(13L);
        assertEquals(1, phoneNumbers.size());
        assertEquals("+7 9111111111", phoneNumbers.get(0));
    }

    @Test
    void testGetEmailsByClientIdSuccess() {
        Email email = Email.builder().email("roma@example.com").build();
        client.getEmails().add(email);
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        List<String> emails = clientService.getEmailsByClientId(13L);
        verify(clientRepository).findById(13L);
        assertEquals(1, emails.size());
        assertEquals("roma@example.com", emails.get(0));
    }

}
