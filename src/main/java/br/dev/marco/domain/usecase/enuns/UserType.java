package br.dev.marco.domain.usecase.enuns;

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
