package com.github.riset_backend.menu.entity;

import com.github.riset_backend.file.entity.File;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Column(name = "menu_url")
    private String menuUrl;
}
