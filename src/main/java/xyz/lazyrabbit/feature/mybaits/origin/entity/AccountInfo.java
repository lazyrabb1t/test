package xyz.lazyrabbit.feature.mybaits.origin.entity;

public class AccountInfo {

    private Integer id;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
