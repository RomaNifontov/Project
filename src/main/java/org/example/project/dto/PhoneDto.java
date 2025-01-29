package org.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneDto {

    @NotNull(message = "ClientId cannot be null")
    private Long clientId;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

}
