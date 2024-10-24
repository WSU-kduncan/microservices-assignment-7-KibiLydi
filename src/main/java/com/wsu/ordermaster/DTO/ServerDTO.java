package com.wsu.ordermaster.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerDTO
{

    private Integer id;
    @NotBlank(message = "First Name must not be null or blank")
    @Size(max = 35)
    private String firstName;
    @NotBlank(message = "Last Name must not be null or blank")
    @Size(max = 35)
    private String lastName;
    @NotBlank(message = "Availability must not be null or blank")
    @Size(max = 10)
    private String availability;
}
