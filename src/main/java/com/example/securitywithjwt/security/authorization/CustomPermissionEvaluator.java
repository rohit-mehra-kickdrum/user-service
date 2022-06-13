package com.example.securitywithjwt.security.authorization;

import com.example.securitywithjwt.models.entity.Role;
import com.example.securitywithjwt.models.entity.User;
import com.example.securitywithjwt.repositories.RoleRepository;
import com.example.securitywithjwt.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    final private UserRepository userRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }
        String targetType = targetDomainObject.toString().toUpperCase();
        return hasPrivilege(authentication, targetType, permission.toString().toUpperCase());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)){
            return false;
        }
        return hasPrivilege(authentication, targetType.toUpperCase(), permission.toString().toUpperCase());
    }

    private boolean hasPrivilege(Authentication authentication, String targetType, String permission) {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().contains(targetType) && hasRequiredPermission(authentication,targetType, permission)){
                return true;
            }
        }
        return false;
    }

    private boolean hasRequiredPermission(Authentication authentication, String targetType, String permission) {
        User user = userRepository.findByUsername(authentication.getPrincipal().toString());
        Collection<Role> roles = user.getRoles();
        for (Role role : roles){
            if (role.getName().equals(targetType)){
                return role.getPrivileges().stream().anyMatch(privilege -> privilege.equals(permission));
            }
        }
        return false;
    }


}
