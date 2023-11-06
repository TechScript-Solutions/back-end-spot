package com.techscript.spot82.services;

import com.techscript.spot82.dtos.UserDTO;
import com.techscript.spot82.entities.User;
import com.techscript.spot82.exceptions.UserExceptions;
import com.techscript.spot82.respository.UsuarioRespository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServices {

    private UsuarioRespository usuarioRespository;

    public User save(User user) {

        if (usuarioRespository.existsByNickname(user.getNickname())) {
            throw new UserExceptions("E-mail de usuário já cadastrado no sistema.");
        } else if (!user.getPassword().equalsIgnoreCase(user.getConfirmPassword())) {
            throw new UserExceptions("As senhas inseridas não conferem.");
        } else if (usuarioRespository.existsByContact(user.getContact())) {
            throw new UserExceptions("Contato já registrado no sistema. Por favor, escolha outro");

        }

        return usuarioRespository.save(user);
    }

    public List<UserDTO> listar() {
        List<User> users = usuarioRespository.findAll();
        List<UserDTO> usuarioDTOs = new ArrayList<>();

        if (users.isEmpty()) {
            throw new UserExceptions("Não há usuários cadastrados.");
        }

        for (User user : users) {
            UserDTO usuarioDTO = new UserDTO();
            BeanUtils.copyProperties(user, usuarioDTO);
            usuarioDTOs.add(usuarioDTO);
        }

        return usuarioDTOs;

    }

    public UserDTO buscarPorNick(String nickname) {

        try {

            User user = usuarioRespository.nickname(nickname).get();
            var usuarioDTO = new UserDTO();

            BeanUtils.copyProperties(user, usuarioDTO);

            return usuarioDTO;

        } catch (NoSuchElementException e) {

            throw new UserExceptions("Usuário não encontrado");

        }

    }

    public User alterarDados(String nickname, User usuario) {

        try {

            if (usuarioRespository.existsByNickname(usuario.getNickname())) {
                throw new UserExceptions("Nickname já registrado no sistema. Por favor, escolha outro");
            }

            var user = usuarioRespository.nickname(nickname).get();

            usuario.setId(user.getId());
            BeanUtils.copyProperties(usuario, user);
            usuarioRespository.save(user);

            return user;

        } catch (NoSuchElementException e) {
            throw new UserExceptions("Usuário não encontrado.");
        }

    }

    @Transactional
    public void removerUsuario(String nickname) {
        usuarioRespository.deleteByNickname(nickname);
    }

}
