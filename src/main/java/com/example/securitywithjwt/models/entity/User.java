package com.example.securitywithjwt.models.entity;

import com.example.securitywithjwt.models.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "Name of the user", example = "John Doe")
    private String name;
    @ApiModelProperty(notes = "Unique username", example = "john@doe")
    private String username;
    @ApiModelProperty(notes = "Password of the user", example = "john@123")
    private String password;
    // load all the roles whenever user is loaded
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
