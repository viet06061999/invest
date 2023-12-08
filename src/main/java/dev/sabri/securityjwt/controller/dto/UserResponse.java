package dev.sabri.securityjwt.controller.dto;

import dev.sabri.securityjwt.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse  {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    Role role;
}
