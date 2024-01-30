package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role", nullable = false, unique = true)
    private String role;

    @Transient
    @ManyToMany(mappedBy = "role")
    private Set<User> userByrole = new HashSet<>();

    public Role() {}

    public Role(int id) {this.id = id;}

    public Role(String role) {this.role = role;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public Set<User> getUserByrole() {return userByrole;}
    public void setUserByrole(Set<User> userByrole) {this.userByrole = userByrole;}

    @Override
    public String toString() {return role;}

    @Override
    public String getAuthority() {return role;}
}
