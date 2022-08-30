package com.broadfactor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4991871408064598402L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private String username;

    @NotBlank(message = "{email.not-blank}")
    @Email(message = "{email.not-valid}")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "{password.not-blank}")
    @Length(min = 6, message = "{password.min-caracter-6}")
    private String password;

}
