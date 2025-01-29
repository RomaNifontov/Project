package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.dto.EmailRequestDto;
import org.example.project.dto.PhoneRequestDto;
import org.example.project.mapper.ClientMapper;
import org.example.project.model.Client;
import org.example.project.model.Email;
import org.example.project.model.PhoneNumber;
import org.example.project.repository.ClientRepository;
import org.example.project.validator.ClientValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        clientValidator.validate(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmails(new HashSet<>());
        client.setPhoneNumbers(new HashSet<>());
        clientRepository.save(client);
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public ClientResponseDto addNewEmail(EmailRequestDto emailRequestDto) {
        clientValidator.validateEmail(emailRequestDto.getEmail());
        Client client = getClientFromDB(emailRequestDto.getClientId());
        Set<Email> emails = client.getEmails();
        emails.add(Email.builder()
                .email(emailRequestDto.getEmail())
                .client(client)
                .build());
        client.setEmails(emails);
        clientRepository.save(client);
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public ClientResponseDto addNewTelephoneNumber(PhoneRequestDto phoneRequestDto) {
        clientValidator.validateNumber(phoneRequestDto.getPhoneNumber());
        Client client = getClientFromDB(phoneRequestDto.getClientId());
        Set<PhoneNumber> telephoneNumbers = client.getPhoneNumbers();
        telephoneNumbers.add(PhoneNumber.builder()
                .number(phoneRequestDto.getPhoneNumber())
                .client(client)
                .build());
        client.setPhoneNumbers(telephoneNumbers);
        clientRepository.save(client);
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clientMapper.toClientDtos(clients);
    }

    @Transactional
    public ClientResponseDto getClientById(Long clientId) {
        Client client = getClientFromDB(clientId);
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public List<String> getPhoneNumbersByClientId(Long clientId) {
        Client client = getClientFromDB(clientId);
        return client.getPhoneNumbers().stream().map(PhoneNumber::getNumber).toList();
    }

    @Transactional
    public List<String> getEmailsByClientId(Long clientId) {
        Client client = getClientFromDB(clientId);
        return client.getEmails().stream().map(Email::getEmail).toList();
    }

    private Client getClientFromDB(Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new IllegalArgumentException("Client with id " + clientId + " does not exist");
        }
        return optionalClient.get();
    }

}
