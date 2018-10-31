package com.starodub.model;

import java.util.HashSet;
import java.util.Set;

@TableName(name = "users")
public class User {

    @ColumnName(name = "ID")
    private Long id;
    @ColumnName(name = "EMAIL")
    private String email;
    @ColumnName(name = "PASSWORD")
    private String password;
    @ColumnName(name = "TOKEN")
    private String token;
    @ColumnName(name = "FIRST_NAME")
    private String firstName;
    @ColumnName(name = "LAST_NAME")
    private String lastName;
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long id, String email, String password, String token, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
