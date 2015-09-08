package com.rjuarez.core.manager;

import java.util.List;

import com.rjuarez.core.model.Role;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface RoleManager extends GenericService<Role,Long> {

    List<?> getRoles(Role role);

    Role getRole(String rolename);

    Role saveRole(Role role);

    void removeRole(String rolename);
}
