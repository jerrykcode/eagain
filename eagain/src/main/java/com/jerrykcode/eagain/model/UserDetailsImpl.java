package com.jerrykcode.eagain.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Accessors(chain = true)
@Data
public class UserDetailsImpl implements UserDetails, Serializable {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String nickname;
    private Long gmtCreate;
    private Long gmtLastLogin;
    private Integer accountOpts;
    private String avatarUrl;
    private List<GrantedAuthority> authorities;

    //bitmap for accountOpts
    public static final int ACCOUNT_ENABLED = 0x1;
    public static final int ACCOUNT_NON_EXPIRED = 0x2;
    public static final int ACCOUNT_NON_LOCKED = 0x4;
    public static final int CREDENTIALS_NON_EXPIRED = 0x8;

    public static final int ACCOUNT_DEFAULT_SETTING = ACCOUNT_ENABLED | ACCOUNT_NON_EXPIRED | ACCOUNT_NON_LOCKED | CREDENTIALS_NON_EXPIRED;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return (accountOpts & ACCOUNT_NON_EXPIRED) != 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (accountOpts & ACCOUNT_NON_LOCKED) != 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return (accountOpts & CREDENTIALS_NON_EXPIRED) != 0;
    }

    @Override
    public boolean isEnabled() {
        return (accountOpts & ACCOUNT_ENABLED) != 0;
    }
}
