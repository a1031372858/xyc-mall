package org.xyc.app.login.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.xyc.app.basic.model.SecurityUser;
import org.xyc.domain.base.util.Assert;
import org.xyc.domain.user.facade.UserReadFacade;
import org.xyc.domain.user.model.to.UserTO;

import java.util.Objects;

/**
 * @author xuyachang
 * @date 2024/3/20
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserReadFacade userReadFacade;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserTO userTO = getUserInfo(username);
        String password = StringUtils.EMPTY;
        if(Objects.nonNull(userTO)){
            password = userTO.getPassword();
        }
        SecurityUser securityUser = new SecurityUser(username, password, AuthorityUtils.createAuthorityList());
        securityUser.setUserTO(userTO);
        return securityUser;
    }

    private UserTO getUserInfo(String username){
        UserTO userTO = new UserTO();
        userTO.setPhone(username);
        return Assert.getResult(userReadFacade.findUserByPhone(userTO));
    }
}
