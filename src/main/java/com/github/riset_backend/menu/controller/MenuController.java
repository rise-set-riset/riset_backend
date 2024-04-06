package com.github.riset_backend.menu.controller;

import com.github.riset_backend.menu.dto.MenuDTO;
import com.github.riset_backend.menu.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @GetMapping
    public MenuResponseDTO getMenus() {
        // 유저 정보 생성
        UserDTO user = new UserDTO("유저 이미지 url", "홍길동", "사원");

        // 메뉴 정보 생성
        List<MenuDTO> menus = new ArrayList<>();

        // 홈 메뉴
        MenuDTO homeMenu = MenuDTO.builder()
                .id(1L)
                .title("홈")
                .icon("home")
                .link("/home")
                .build();

        // 출퇴근 메뉴
        MenuDTO commuteMenu = MenuDTO.builder()
                .id(2L)
                .title("출퇴근")
                .icon("commute")
                .link("/commute")
                .build();

        // 게시판 메뉴와 서브 메뉴
        MenuDTO postListMenu = MenuDTO.builder()
                .id(3L)
                .title("게시판")
                .icon("board")
                .link("/postlist")
                .build();

        MenuDTO postListSubMenu = MenuDTO.builder()
                .id(4L)
                .title("게시글 목록")
                .link("/postlist")
                .build();

        MenuDTO myPostSubMenu = MenuDTO.builder()
                .id(5L)
                .title("내 게시글")
                .link("/mypost")
                .build();

        List<MenuDTO> postListSubMenus = new ArrayList<>();
        postListSubMenus.add(postListSubMenu);
        postListSubMenus.add(myPostSubMenu);

        postListMenu.setSubMenus(postListSubMenus);

        // 일정 메뉴와 서브 메뉴
        MenuDTO officialPlanMenu = MenuDTO.builder()
                .id(6L)
                .title("일정")
                .icon("officialPlan")
                .link("/official-plan")
                .build();

        MenuDTO companyScheduleSubMenu = MenuDTO.builder()
                .id(7L)
                .title("회사 일정")
                .link("/official-plan")
                .build();

        MenuDTO workScheduleSubMenu = MenuDTO.builder()
                .id(8L)
                .title("근무 일정")
                .link("/personal-plan")
                .build();

        List<MenuDTO> officialPlanSubMenus = new ArrayList<>();
        officialPlanSubMenus.add(companyScheduleSubMenu);
        officialPlanSubMenus.add(workScheduleSubMenu);

        officialPlanMenu.setSubMenus(officialPlanSubMenus);

        // 휴가 메뉴와 서브 메뉴
        MenuDTO dayoffMenu = MenuDTO.builder()
                .id(9L)
                .title("휴가")
                .icon("applyDayoff")
                .link("/apply-dayoff")
                .build();

        MenuDTO applyDayoffSubMenu = MenuDTO.builder()
                .id(10L)
                .title("휴가 신청")
                .link("/apply-dayoff")
                .build();

        MenuDTO dayoffStatusSubMenu = MenuDTO.builder()
                .id(11L)
                .title("휴가 현황")
                .link("/accept-dayoff")
                .build();

        MenuDTO approveDayoffSubMenu = MenuDTO.builder()
                .id(12L)
                .title("휴가 승인")
                .link("/current-dayoff")
                .build();

        List<MenuDTO> dayoffSubMenus = new ArrayList<>();
        dayoffSubMenus.add(applyDayoffSubMenu);
        dayoffSubMenus.add(dayoffStatusSubMenu);
        dayoffSubMenus.add(approveDayoffSubMenu);

        dayoffMenu.setSubMenus(dayoffSubMenus);

        // 정산 메뉴
        MenuDTO salaryMenu = MenuDTO.builder()
                .id(13L)
                .title("정산")
                .icon("salary")
                .link("/salary")
                .build();

        // 조직 메뉴와 서브 메뉴
        MenuDTO groupChartMenu = MenuDTO.builder()
                .id(14L)
                .title("조직")
                .icon("groupChart")
                .link("/group-chart")
                .build();

        MenuDTO orgChartSubMenu = MenuDTO.builder()
                .id(15L)
                .title("조직도")
                .link("/group-chart")
                .build();

        MenuDTO memberProfileSubMenu = MenuDTO.builder()
                .id(16L)
                .title("조직원 프로필")
                .link("/group-member")
                .build();

        List<MenuDTO> groupChartSubMenus = new ArrayList<>();
        groupChartSubMenus.add(orgChartSubMenu);
        groupChartSubMenus.add(memberProfileSubMenu);

        groupChartMenu.setSubMenus(groupChartSubMenus);

        // 관리 메뉴
        MenuDTO settingMenu = MenuDTO.builder()
                .id(17L)
                .title("관리")
                .icon("setting")
                .link("/setting")
                .build();

        // 메뉴들을 리스트에 추가
        menus.add(homeMenu);
        menus.add(commuteMenu);
        menus.add(postListMenu);
        menus.add(officialPlanMenu);
        menus.add(dayoffMenu);
        menus.add(salaryMenu);
        menus.add(groupChartMenu);
        menus.add(settingMenu);

        // MenuResponseDTO 객체 생성 후 반환
        return new MenuResponseDTO(user, menus);
    }

    // MenuResponseDTO 클래스 정의
    @Getter
    @Setter
    private static class MenuResponseDTO {
        private UserDTO user;
        private List<MenuDTO> menus;

        public MenuResponseDTO(UserDTO user, List<MenuDTO> menus) {
            this.user = user;
            this.menus = menus;
        }
    }
}