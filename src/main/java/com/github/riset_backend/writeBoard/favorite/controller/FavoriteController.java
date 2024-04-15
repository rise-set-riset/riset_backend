package com.github.riset_backend.writeBoard.favorite.controller;

import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import com.github.riset_backend.writeBoard.favorite.dto.FavoriteResponseDto;
import com.github.riset_backend.writeBoard.favorite.dto.FavoriteUpdateRequestDto;
import com.github.riset_backend.writeBoard.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping()
    public ResponseEntity<List<FavoriteResponseDto>> getAllFavoriteBoard (@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        List<FavoriteResponseDto> favorites = favoriteService.getAllFavoriteBoard(customUserDetails.getEmployee(), page, size);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/{boardNo}")
    public ResponseEntity<List<FavoriteResponseDto>> createFavoriteBoard (@PathVariable Long boardNo,
                                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<FavoriteResponseDto> favorites = favoriteService.createFavoriteBoard(boardNo, customUserDetails.getEmployee());
        return ResponseEntity.ok(favorites);
    }

    @PatchMapping("/update/{boardNo}")
    public ResponseEntity<List<FavoriteResponseDto>> updateFavoriteBoard (@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                    @RequestBody FavoriteUpdateRequestDto favoriteUpdateRequestDto,
                                                                    @PathVariable Long boardNo
                                                                    ) {
        List<FavoriteResponseDto> favorites = favoriteService.updateFavoriteBoard(boardNo, customUserDetails.getEmployee(), favoriteUpdateRequestDto);
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("/delete/{favoriteId}")
    public ResponseEntity<List<FavoriteResponseDto>> deleteFavoriteBoard (@PathVariable Long favoriteId,
                                                                    @AuthenticationPrincipal CustomUserDetails customUserDetails
                                                       ) {
        List<FavoriteResponseDto> favorites = favoriteService.deleteFavoriteBoard(favoriteId, customUserDetails.getEmployee());
        return ResponseEntity.ok(favorites);
    }

}

