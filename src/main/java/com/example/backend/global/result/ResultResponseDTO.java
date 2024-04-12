package com.example.backend.global.result;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ResultResponseDTO {

    private int status; // 상태 코드
    private String message; // 상태 메시지
    private Object data; // 전달할 객체

    public ResultResponseDTO(ResultCodeMessage resultCodeMessage, Object data) {
        this.status = resultCodeMessage.getStatus();
        this.message = resultCodeMessage.getMessage();
        this.data = data;
    }

    public static ResultResponseDTO of(ResultCodeMessage resultCodeMessage, Object data) {
        return new ResultResponseDTO(resultCodeMessage, data);
    }

   public static ResultResponseDTO of(ResultCodeMessage resultCodeMessage) {
        return new ResultResponseDTO(resultCodeMessage, "");
   }

/* TODO

 */


}





















