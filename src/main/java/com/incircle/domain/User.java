package com.incircle.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username can't be empty")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    private String username;

    @NotBlank(message = "Password can't be empty")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Password can only contain letters and numbers")
    private String password;

    private boolean active = true;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Contact> contacts;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setUser(this);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}