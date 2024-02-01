package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name"
//            , nullable = false, unique = true
    )
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {}
    public Role(Long id){ this.id = id;}
    public Role(String name){ this.name = name;}
    public Role(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {return id;}
    public String getName() {return name;}

//    public Set<User> getUserByrole() {return userByrole;}
//    public void setUserByrole(Set<User> userByrole) {this.userByrole = userByrole;}


    @Override
    public String getAuthority() {return getName();}
}
