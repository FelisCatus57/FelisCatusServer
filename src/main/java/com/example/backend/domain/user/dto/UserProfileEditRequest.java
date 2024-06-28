package com.example.backend.domain.user.dto;

import com.example.backend.domain.user.Enum.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "유저 정보 수정 요청 DTO")
public class UserProfileEditRequest {

    @Schema(description = "유저 웹사이트")
    private String website;

    @Schema(description = "유저 소개글")
    private String introduce;

    @Schema(description = "유저 번호")
    private String phoneNo;

    @Schema(description = "유저 성별")
    private Gender gender;

}
