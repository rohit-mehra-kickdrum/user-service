package com.example.securitywithjwt.models.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddUserRoleDTO {
    @ApiModelProperty(notes = "Unique username of the user", example = "john@doe")
    private String username;

    @ApiModelProperty(notes = "Role Name which is present on the DB", example = "ROLE_USER")
    private String roleName;
}
