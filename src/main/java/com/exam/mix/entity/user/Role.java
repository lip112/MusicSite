package com.exam.mix.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Getter
public enum Role {

    //name      getValue()
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String value;

    Role(String value) {
        this.value = value;
    }

    Role() {
        this.value = "ROLE_USER";
    }

    public void RoleUpgrade() {
        this.value = "ROLE_ADMIN";
    }

}
