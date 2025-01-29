package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.dto.EmailDto;
import org.example.project.dto.PhoneDto;
import org.example.project.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/clients")
@Validated
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDto createClient(@Valid  @RequestBody ClientRequestDto clientRequestDto) {
        return clientService.createClient(clientRequestDto);
    }

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public EmailDto addNewEmail(@Valid @RequestBody EmailDto emailDto) {
        return clientService.addNewEmail(emailDto);
    }

    @PostMapping("/phone")
    @ResponseStatus(HttpStatus.OK)
    public PhoneDto addNewPhone(@Valid @RequestBody PhoneDto phoneDto) {
        return clientService.addNewTelephoneNumber(phoneDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientResponseDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientResponseDto getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping("/{clientId}/phone")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getPhoneNumbersByClientId(@PathVariable Long clientId) {
        return clientService.getPhoneNumbersByClientId(clientId);
    }

    @GetMapping("/{clientId}/email")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getEmailsByClientId(@PathVariable Long clientId) {
        return clientService.getEmailsByClientId(clientId);
    }

    @GetMapping("/{clientId}/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getEmailsWithFilter(@PathVariable Long clientId, @RequestParam(name = "filter") String filter) {
        return clientService.getEmailsWithFilter(clientId, filter);
    }

}
