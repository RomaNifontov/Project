package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.dto.EmailDto;
import org.example.project.dto.PhoneDto;
import org.example.project.mapper.ClientMapper;
import org.example.project.model.Client;
import org.example.project.model.Email;
import org.example.project.model.PhoneNumber;
import org.example.project.repository.ClientRepository;
import org.example.project.repository.EmailRepository;
import org.example.project.repository.PhoneRepository;
import org.example.project.validator.ClientValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final ClientMapper clientMapper;

    @Transactional
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        clientValidator.validate(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        clientRepository.save(client);
        log.info("Client created: {}", client.getName());
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public EmailDto addNewEmail(EmailDto emailDto) {
        clientValidator.validateEmail(emailDto.getEmail());
        Client client = getClientFromDB(emailDto.getClientId());
        Email email = new Email();
        email.setEmail(emailDto.getEmail());
        email.setClient(client);
        emailRepository.save(email);
        log.info("Email {} added to client {}", emailDto.getEmail(), client.getName());
        return emailDto;
    }

    @Transactional
    public PhoneDto addNewTelephoneNumber(PhoneDto phoneDto) {
        clientValidator.validateNumber(phoneDto.getPhoneNumber());
        Client client = getClientFromDB(phoneDto.getClientId());
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setNumber(phoneDto.getPhoneNumber());
        phoneNumber.setClient(client);
        phoneRepository.save(phoneNumber);
        log.info("TelephoneNumber {} added to client {}", phoneDto.getPhoneNumber(), client.getName());
        return phoneDto;
    }

    @Transactional
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            log.info("No clients found");
            return new ArrayList<>();
        }
        log.info("All clients found");
        return clientMapper.toClientDtos(clients);
    }

    @Transactional
    public ClientResponseDto getClientById(Long clientId) {
        Client client = getClientFromDB(clientId);
        log.info("Client found: {}", client.getName());
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public List<String> getPhoneNumbersByClientId(Long clientId) {
        Client client = getClientFromDB(clientId);
        log.info("TelephoneNumbers found {}", client.getPhoneNumbers());
        return client.getPhoneNumbers().stream().map(PhoneNumber::getNumber).toList();
    }

    @Transactional
    public List<String> getEmailsByClientId(Long clientId) {
        Client client = getClientFromDB(clientId);
        return client.getEmails().stream().map(Email::getEmail).toList();
    }

    @Transactional
    public List<String> getEmailsWithFilter(Long clientId, String filter) {
        Client client = getClientFromDB(clientId);
        Set<Email> emails = client.getEmails();
        log.info("Emails with filter {} found", filter);
        return emails.stream().map(Email::getEmail).filter(email -> email.contains(filter)).toList();
    }

    private Client getClientFromDB(Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new IllegalArgumentException("Client with id " + clientId + " does not exist");
        }
        return optionalClient.get();
    }

}
