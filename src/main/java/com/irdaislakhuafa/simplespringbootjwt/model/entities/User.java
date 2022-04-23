package com.irdaislakhuafa.simplespringbootjwt.model.entities;

import java.util.Collection;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User extends BaseEntity implements UserDetails {

    @Indexed(unique = true)
    private String email;

    @Indexed
    private String username;

    private String password;

    @Builder.Default
    private boolean isEnable = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnable;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }
}
