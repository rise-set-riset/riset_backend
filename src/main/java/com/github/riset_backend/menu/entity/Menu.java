package com.github.riset_backend.menu.entity;

import com.github.riset_backend.file.entity.File;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Setter
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Column(name = "title")
    private String title;

    @Column(name = "icon")
    private String icon;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Menu> subMenus;

    @Column(name = "menu_order")
    private Integer menuOrder;

    @Column(name = "menu_url")
    private String menuUrl;

    public Menu() {
    }
}
