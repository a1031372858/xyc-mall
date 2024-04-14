package org.xyc.app.basic.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.xyc.domain.user.model.to.UserTO;

import java.util.Collection;

/**
 * @author xuyachang
 * @date 2024/4/6
 */
public class SecurityUser extends User {

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private UserTO userTO;

    public UserTO getUserTO() {
        return userTO;
    }

    public void setUserTO(UserTO userTO) {
        this.userTO = userTO;
    }
}
