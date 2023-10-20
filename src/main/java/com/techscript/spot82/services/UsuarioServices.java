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
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UsuarioServices {

    private UsuarioRespository usuarioRespository;

    public Usuario save(Usuario usuario) {

        if (usuarioRespository.existsByNickname(usuario.getNickname())) {
            throw new UsuarioExceptions("E-mail de usuário já cadastrado no sistema.");
        } else if (!usuario.getPassword().equalsIgnoreCase(usuario.getConfirmaPassword())) {
            throw new UsuarioExceptions("As senhas inseridas não conferem.");
        }

        return usuarioRespository.save(usuario);
    }

    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioRespository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

        if (usuarios.isEmpty()) {
            throw new UsuarioExceptions("Não há usuários cadastrados.");
        }

        for (Usuario usuario : usuarios) {
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            BeanUtils.copyProperties(usuario, usuarioDTO);
            usuarioDTOs.add(usuarioDTO);
        }

        return usuarioDTOs;

    }

    public UsuarioDTO buscarPorNick(String nickname) {

        try {

            Usuario usuario = usuarioRespository.nickname(nickname).get();
            var usuarioDTO = new UsuarioDTO();

            BeanUtils.copyProperties(usuario, usuarioDTO);

            return usuarioDTO;

        } catch (NoSuchElementException e) {

            throw new UsuarioExceptions("Usuário não encontrado");

        }

    }

    public Usuario alterarDados(String nickname, Usuario usuario) {

        try {

            if (usuarioRespository.existsByNickname(usuario.getNickname())) {
                throw new UsuarioExceptions("Nickname já registrado no sistema. Por favor, escolha outro");

            } else if (usuarioRespository.existsByContato(usuario.getContato())) {
                throw new UsuarioExceptions("Contato já registrado no sistema. Por favor, escolha outro");

            }

            var user = usuarioRespository.nickname(nickname).get();

            usuario.setId(user.getId());
            BeanUtils.copyProperties(usuario, user);
            usuarioRespository.save(user);

            return user;

        } catch (NoSuchElementException e) {
            throw new UsuarioExceptions("Usuário não encontrado.");
        }

    }

}
