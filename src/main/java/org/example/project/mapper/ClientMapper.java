package org.example.project.mapper;

import org.example.project.dto.ClientRequestDto;
import org.example.project.dto.ClientResponseDto;
import org.example.project.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client toClient(ClientRequestDto clientRequestDto);

    ClientResponseDto toClientDto(Client client);

    List<ClientResponseDto> toClientDtos(List<Client> clients);

}
