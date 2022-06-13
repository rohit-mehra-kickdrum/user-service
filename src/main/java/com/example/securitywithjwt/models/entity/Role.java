package com.example.securitywithjwt.models.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "Unique role for the user", example = "ROLE_USER/ROLE_ADMIN")
    private String name;
    @ElementCollection
    private Collection<String> privileges = new ArrayList<>();
}

