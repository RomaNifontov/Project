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
public class EmailDto {

    @NotNull(message = "ClientId cannot be null")
    private Long clientId;
    @NotBlank(message = "Email cannot be blank")
    private String email;

}
