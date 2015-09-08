package com.rjuarez.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

/**
 * This class is used to represent available roles in the database.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Version by
 *         Dan Kibler dan@getrolling.com Extended to implement Acegi
 *         GrantedAuthority interface by David Carter david@carter.net
 */
@Entity
@Table(name = "role")
@NamedQueries({ @NamedQuery(name = "findRoleByName", query = "select r from Role r where r.name = :name ") })
public class Role implements Serializable, GrantedAuthority {
    private static final int DESCRIPTION_LENGTH = 140;
    private static final int NAME_LENGTH = 20;
    private static final long serialVersionUID = 3690197650654049848L;
    private Long id;
    private String name;
    private String description;

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public Role() {
    }

    /**
     * Create a new instance and set the name.
     *
     * @param name
     *            name of the role.
     */
    public Role(final String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @return the name property (getAuthority required by Acegi's
     *         GrantedAuthority interface)
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Transient
    public String getAuthority() {
        return getName();
    }

    @Column(length = NAME_LENGTH)
    public String getName() {
        return this.name;
    }

    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return this.description;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Role other = (Role) obj;

        return Objects.equals(this.name, other.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(this.name).toString();
    }
}
