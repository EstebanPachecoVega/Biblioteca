package cl.ecomarket.usuario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios",uniqueConstraints = @UniqueConstraint(columnNames = "correo"))
@Data
@NoArgsConstructor

public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String pnombre;

@Column(nullable = false)
private String snombre;

@Column(nullable = false)
private String appaterno;

@Column(nullable = false)
private String apmaterno;

@Column(nullable = false,unique = true)
private String correo;

@Column(nullable = false)
private String password; //pswd

@Column(nullable = false)
private String rol; 

@Column(nullable = false)
private boolean activa=true;
}
