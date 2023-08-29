package br.dev.marco.usecase.enuns;

public enum UserType {
    FREE("simple-user"),
    MEMBER("member-user");

    private String roleName;

    UserType(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }
}
