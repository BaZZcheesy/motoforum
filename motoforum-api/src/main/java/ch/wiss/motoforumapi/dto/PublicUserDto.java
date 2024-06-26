package ch.wiss.motoforumapi.dto;

import java.util.Set;

import ch.wiss.motoforumapi.models.Role;

public class PublicUserDto {
    private Long id;
    private String username;
    private String motorcycle;
    private Set<Role> roles;

    public PublicUserDto() {

    }

    public PublicUserDto(Long id, String username, String motorcycle, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.motorcycle = motorcycle;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMotorcycle() {
        return motorcycle;
    }

    public void setMotorcycle(String motorcycle) {
        this.motorcycle = motorcycle;
    }

    
}
