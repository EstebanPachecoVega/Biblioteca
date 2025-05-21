package cl.ecomarket.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioDTO {
private Long id;

@NotBlank
private String pnombre;

@NotBlank
private String snombre;

@NotBlank
private String appaterno;

@NotBlank
private String apmaterno;

@Email
@NotBlank
private String correo;

@NotBlank
@Size(min = 6)
private String password;

@NotBlank
private String rol;


private boolean activa;
}
