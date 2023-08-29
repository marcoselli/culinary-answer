package br.dev.marco.usecase.enuns;

public enum UserType {
    FREE("free_user"),
    MEMBER("member_user");

    private String roleName;

    UserType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
