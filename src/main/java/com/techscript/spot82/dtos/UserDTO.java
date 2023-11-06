package com.techscript.spot82.dtos;

import com.techscript.spot82.entities.User;
import com.techscript.spot82.enums.Roles;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private String name;
    private String nickname;
    private String contact;
    private Roles roles;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.contact = user.getContact();
        this.roles = user.getRoles();
    }
}