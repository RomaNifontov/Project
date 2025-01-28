package org.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<String> emails;
    private Set<String> phoneNumbers;
    private LocalDateTime createdAt;
}
