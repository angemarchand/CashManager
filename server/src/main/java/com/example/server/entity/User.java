package com.example.server.entity;

import com.example.server.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = true )
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @Setter(AccessLevel.NONE)
    private String password;

    @Enumerated
    @Column(name = "role", nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    private Set<Account> accounts = new LinkedHashSet<>();



    public User(String firstname, String lastname, String email, String password, UserRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        setPassword(password);
        this.role = role;
    }



    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }
}





