package org.teamseven.hms.backend.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "useraccount")
public class User implements UserDetails {

    @Id
    @Column(name="userid", insertable=false)
    @GeneratedValue
    private UUID userid;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String password;
    private String date_of_birth;
    @NotNull
    private String email;
    private String phone;
    private String nric;
    private String address;
    @Column(columnDefinition = "VARCHAR(2)")
    private String gender;
    private String type;
    @Column(name="is_active", insertable = false)
    private Integer is_active = 1;
    @Column(name="created_at", insertable = false)
    private OffsetDateTime created_at = OffsetDateTime.now();
    private OffsetDateTime modified_at;



    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}
