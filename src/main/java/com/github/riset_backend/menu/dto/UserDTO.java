package com.github.riset_backend.menu.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String icon;
    private String name;
    private String rank;
}