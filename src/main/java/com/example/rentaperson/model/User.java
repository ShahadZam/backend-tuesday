package com.example.rentaperson.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "role cannot be empty")
    @Pattern(regexp = "(ADMIN|USER|PERSON)",message = "Role must be in (ADMIN|USER|PERSON)")
    private String role;

    @NotEmpty(message = "username cannot be empty")
    @Column(unique = true)
    private String username;


    @NotEmpty(message = "password cannot be empty")
    private String password;

    @Email(message = "Email has to be in email format")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "city cannot be empty")
    private String city;

    private String description;//provider

    private Double pricePerHour = Double.valueOf(0);//provider

    @Pattern(regexp = "(Tutoring|Gaming|Travelling|Shopping|Cooking|Art|Party Hosting|Baby Sitter)",message = "category must be right ")
    private String category;//provider

    @URL(message = "image must be a URL")
    private String image;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
