package com.kapia.jobboard.api.auth.model;

import com.kapia.jobboard.api.data.constants.Defaults;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * This class represents an application user.
 */
@Entity
@Table(name = "users")
public class AppUser implements UserDetails {

    /**
     * The ID of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     */
    @NotBlank
    private String username;

    /**
     * The email of the user.
     */
    @Email
    @NotBlank
    private String email;

    /**
     * The password of the user.
     */
    @NotBlank
    private String password;

    /**
     * The date the user was created.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    /**
     * The date the user was last updated.
     */
    @UpdateTimestamp
    @Column(updatable = false)
    private Date updatedAt;

    /**
     * The role of the user.
     */
    @ManyToOne
    private Role role;

    /**
     * Default constructor for the AppUser class.
     */
    public AppUser() {
    }

    /**
     * Constructor for the AppUser class.
     *
     * @param id The ID of the user.
     * @param username The username of the user.
     * @param email The email of the user.
     * @param password The password of the user.
     * @param createdAt The date the user was created.
     * @param updatedAt The date the user was last updated.
     */
    public AppUser(Long id, String username, String email, String password, Date createdAt, Date updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Helper method to resolve the authorities of the user.
     *
     * @return The authorities of the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> Defaults.ROLE_PREFIX + role.getName().getValue());
    }

    /**
     * Get the password of the user.
     *
     * @return The password of the user.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Check if the user account is expired.
     *
     * @return True.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the user account is locked.
     *
     * @return True.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the user credentials are expired.
     *
     * @return True.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user account is enabled.
     *
     * @return True.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Get the ID of the user.
     *
     * @return The ID of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the user.
     *
     * @param id The ID of the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the email of the user.
     *
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user.
     *
     * @param email The email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the date the user was created.
     *
     * @return The date the user was created.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the date the user was created.
     *
     * @param createdAt The date the user was created.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Set the date the user was created.
     */
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

    /**
     * Get the date the user was last updated.
     *
     * @return The date the user was last updated.
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Set the date the user was last updated.
     *
     * @param updatedAt The date the user was last updated.
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Set the date the user was last updated.
     */
    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    /**
     * Get the role of the user.
     *
     * @return The role of the user.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set the role of the user.
     *
     * @param role The role of the user.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * The string representation of the AppUser object.
     * @return The string representation of the AppUser object.
     */
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
