package com.quizguru.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private String id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public static UserPrincipal create(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role r : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getName()));
        }
        return new UserPrincipal(user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities);
    }

}
