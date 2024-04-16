package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.myPage.entity.MyImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImgResponseDto {

    private Long profileImgId;
    private String profileImgName;
    private String profileImgPath;

    public ProfileImgResponseDto(MyImage myImage) {
        this.profileImgId = myImage.getMyImageId();
        this.profileImgName = myImage.getFileName();
        this.profileImgPath = myImage.getFilePath();
    }
}
