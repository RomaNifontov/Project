package org.example.project.service;

import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.dto.EmailDto;
import org.example.project.dto.PhoneDto;
import org.example.project.mapper.ClientMapperImpl;
import org.example.project.model.Client;
import org.example.project.model.Email;
import org.example.project.model.PhoneNumber;
import org.example.project.repository.ClientRepository;
import org.example.project.repository.EmailRepository;
import org.example.project.repository.PhoneRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Captor
    private ArgumentCaptor<Client> clientCaptor;
    @Captor
    private ArgumentCaptor<PhoneNumber> phoneNumberCaptor;
    @Captor
    private ArgumentCaptor<Email> emailCaptor;

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private EmailRepository emailRepository;
    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private ClientValidator clientValidator;
    @Spy
    private ClientMapperImpl clientMapper;

    private ClientRequestDto requestDto;
    private EmailDto emailDto;
    private PhoneDto phoneDto;
    private Client client;

    @BeforeEach
    void setUp() {
        requestDto = ClientRequestDto.builder()
                .name("Roma")
                .build();
        emailDto = EmailDto.builder()
                .clientId(13L)
                .email("roma@example.com")
                .build();
        client = Client.builder()
                .id(13L)
                .name("Roma")
                .emails(new HashSet<>())
                .phoneNumbers(new HashSet<>())
                .build();
        phoneDto = PhoneDto.builder()
                .phoneNumber("+7 9111111111")
                .clientId(13L)
                .build();

    }

    @Test
    void testCreateClientSuccess() {
        ClientResponseDto responseDto = clientService.createClient(requestDto);
        verify(clientRepository).save(clientCaptor.capture());
        assertEquals("Roma", clientCaptor.getValue().getName());
        assertEquals("Roma", responseDto.getName());
        assertEquals(0, responseDto.getEmails().size());
    }

    @Test
    void testAddNewEmailSuccess() {
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        EmailDto responseDto = clientService.addNewEmail(emailDto);
        verify(emailRepository).save(emailCaptor.capture());
        assertEquals("roma@example.com", responseDto.getEmail());
    }

    @Test
    void testAddNewPhoneSuccess() {
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        PhoneDto responseDto = clientService.addNewTelephoneNumber(phoneDto);
        verify(phoneRepository).save(phoneNumberCaptor.capture());
        assertEquals("+7 9111111111", responseDto.getPhoneNumber());
    }

    @Test
    void testGetAllClientsSuccess() {
        when(clientRepository.findAll()).thenReturn(List.of(client));
        List<ClientResponseDto> clients = clientService.getAllClients();
        verify(clientRepository).findAll();
        assertEquals(1, clients.size());
        assertEquals("Roma", clients.get(0).getName());
    }

    @Test
    void testGetClientByIdSuccess() {
        when(clientRepository.findById(13L)).thenReturn(Optional.of(client));
        ClientResponseDto responseDto = clientService.getClientById(13L);
        verify(clientRepository).findById(13L);
        assertEquals("Roma", responseDto.getName());
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
