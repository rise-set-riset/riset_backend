package com.github.riset_backend.menu.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Long id;
    private String title;
    private String icon;
    private String link;
    private List<MenuDTO> subMenus;

}
