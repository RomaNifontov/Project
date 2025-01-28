package org.example.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.mapper.ClientMapper;
import org.example.project.model.Client;
import org.example.project.repository.ClientRepository;
import org.example.project.validator.ClientValidator;
import org.springframework.stereotype.Service;

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
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public ClientResponseDto addNewEmail(String email, Long clientId) {
        clientValidator.validateEmail(email);
        Client client = getClientFromDB(clientId);
        Set<String> emails = client.getEmails();
        emails.add(email);
        client.setEmails(emails);
        clientRepository.save(client);
        return clientMapper.toClientDto(client);
    }

    @Transactional
    public ClientResponseDto addNewTelephoneNumber(String telephoneNumber, Long clientId) {
        clientValidator.validateNumber(telephoneNumber);
        Client client = getClientFromDB(clientId);
        Set<String> telephoneNumbers = client.getPhoneNumbers();
        telephoneNumbers.add(telephoneNumber);
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
    public Set<String> getPhoneNumbersByClientId(Long clientId) {
        Client client = getClientFromDB(clientId);
        return client.getPhoneNumbers();
    }

    @Transactional
    public Set<String> getEmailsByClientId(Long clientId) {
        Client client = getClientFromDB(clientId);
        return client.getEmails();
    }

    private Client getClientFromDB(Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new IllegalArgumentException("Client with id " + clientId + " does not exist");
        }
        return optionalClient.get();
    }

}
