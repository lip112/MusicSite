package music.musicsite.entity.user;

import lombok.Getter;

@Getter
public enum ROLE {
    //name          value
    ROLE_USER("ROLE_USER_value"),
    ROLE_MANAGER("ROLE_MANAGER_value");

    private String value;

    ROLE(String value) {
        this.value = value;
    }

    ROLE() {
        this.value = "ROLE_USER";
    }

    public void upgradeRole() {
        this.value = "ROLE_MANAGER";
    }

}
