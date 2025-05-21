package cl.ecomarket.usuario.service;

import cl.ecomarket.usuario.dto.UsuarioDTO;
import cl.ecomarket.usuario.model.Usuario;
import cl.ecomarket.usuario.repository.UsuarioRepositoryJPA;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepositoryJPA repository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private UsuarioDTO toDTO(Usuario usu){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usu.getId());
        dto.setPnombre(usu.getPnombre());       
        dto.setSnombre(usu.getSnombre());
        dto.setAppaterno(usu.getAppaterno());
        dto.setApmaterno(usu.getApmaterno());
        dto.setCorreo(usu.getCorreo());
        dto.setPassword(usu.getPassword());
        dto.setRol(usu.getRol());
        dto.setActiva(usu.isActiva());
        return dto;
        
    }

    public List<UsuarioDTO> getAllUsers(){
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UsuarioDTO getUserById(Long id){
        Usuario usu = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"+id));
        return toDTO(usu);
    }

    @Transactional
    public UsuarioDTO createUser(UsuarioDTO dto){
        repository.findByEmail(dto.getCorreo()).ifPresent(usu ->{
            throw new IllegalArgumentException("Email ya registrado"+dto.getCorreo());
        });
        Usuario usu =new Usuario();
        usu.setId(dto.getId());
        usu.setPnombre(dto.getPnombre());
        usu.setSnombre(dto.getSnombre());
        usu.setAppaterno(dto.getAppaterno());
        usu.setApmaterno(dto.getApmaterno());
        usu.setCorreo(dto.getCorreo());
        usu.setPassword(dto.getPassword());
        usu.setRol(dto.getRol());
        usu.setActiva(true);
        return toDTO(repository.save(usu));
    }

    @Transactional
    public UsuarioDTO updateUser(Long id, UsuarioDTO dto){
        Usuario usu = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + id));
        usu.setId(dto.getId());
        usu.setPnombre(dto.getPnombre());
        usu.setSnombre(dto.getSnombre());
        usu.setAppaterno(dto.getAppaterno());
        usu.setApmaterno(dto.getApmaterno());
        if (!usu.getCorreo().equals(dto.getCorreo())) {
             repository.findByEmail(dto.getCorreo()).ifPresent(ex -> {
                throw new IllegalArgumentException("Email ya registrado: " + dto.getCorreo());
            });
            usu.setCorreo(dto.getCorreo());
        }
        if (dto.getPassword()!=null) {
            usu.setPassword(encoder.encode(dto.getPassword()));
        }
        usu.setRol(dto.getRol());
        return toDTO(repository.save(usu));
    }

    @Transactional
    public void deactivateUser(Long id){
        Usuario usu=repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + id));
        usu.setActiva(false);
        repository.save(usu);

    }
}
