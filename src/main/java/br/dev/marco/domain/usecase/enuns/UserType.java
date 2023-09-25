package br.dev.marco.domain.usecase.enuns;

public enum UserType {
    FREE("free","free-question", "simple-user"),
    MEMBER("member","unlimited-question", "member-user");

    private String type;
    private String roleName;
    private String groupName;

    UserType(String type, String roleName, String groupName) {
        this.type = type;
        this.roleName = roleName;
        this.groupName = groupName;
    }

    public String getType() {
        return this.type;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public String getGroupName() {
        return this.groupName;
    }
}
