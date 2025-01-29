package org.example.project.mapper;

import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.model.Client;
import org.example.project.model.Email;
import org.example.project.model.PhoneNumber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client toClient(ClientRequestDto clientRequestDto);

    @Mapping(source = "emails", target = "emails", qualifiedByName = "mapEmailsToName")
    @Mapping(source = "phoneNumbers", target = "phoneNumbers", qualifiedByName = "mapPhonesToName")
    ClientResponseDto toClientDto(Client client);

    List<ClientResponseDto> toClientDtos(List<Client> clients);

    @Named("mapEmailsToName")
    default List<String> mapEmailsToName(List<Email> emails) {
        return emails.stream()
                .map(Email::getEmail)
                .toList();
    }

    @Named("mapPhonesToName")
    default List<String> mapPhonesToName(List<PhoneNumber> phoneNumbers) {
        return phoneNumbers.stream()
                .map(PhoneNumber::getNumber)
                .toList();
    }

}
