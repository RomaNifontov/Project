package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientResponseDto createClient(@RequestBody ClientRequestDto clientRequestDto) {
        return clientService.createClient(clientRequestDto);
    }

    @PostMapping("/{}")
    public ClientResponseDto addNewEmail(String email, Long clientId) {
        return clientService.addNewEmail(email, clientId);
    }

    public ClientResponseDto addNewPhone(String phone, Long clientId) {
        return clientService.addNewTelephoneNumber(phone, clientId);
    }

    @GetMapping
    public List<ClientResponseDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    public ClientResponseDto getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }
    @GetMapping("/{clientId}/phone")
    public Set<String> getPhoneNumbersByClientId(@PathVariable Long clientId) {
        return clientService.getPhoneNumbersByClientId(clientId);
    }
    @GetMapping("/{clientId}/email")
    public Set<String> getEmailsByClientId(@PathVariable Long clientId) {
        return clientService.getEmailsByClientId(clientId);
    }

}
