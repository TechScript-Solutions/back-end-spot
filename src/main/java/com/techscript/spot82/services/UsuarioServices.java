package com.techscript.spot82.services;

import com.techscript.spot82.dtos.UsuarioDTO;
import com.techscript.spot82.entities.Usuario;
import com.techscript.spot82.exceptions.UsuarioExceptions;
import com.techscript.spot82.respository.UsuarioRespository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioServices {

    private UsuarioRespository usuarioRespository;

    public Usuario save(Usuario usuario) {
        return usuarioRespository.save(usuario);
    }

    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRespository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        if(usuarios.isEmpty()) {
            throw new UsuarioExceptions("Não há usuários cadastrados.");
        }

        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            BeanUtils.copyProperties(usuario, usuarioDTO);
            usuarioDTOs.add(usuarioDTO);
        }


        return usuarioDTOs;

    }

}
