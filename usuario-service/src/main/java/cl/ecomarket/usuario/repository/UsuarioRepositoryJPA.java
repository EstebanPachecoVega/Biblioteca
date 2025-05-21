package cl.ecomarket.usuario.repository;

import cl.ecomarket.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String correo);
}

