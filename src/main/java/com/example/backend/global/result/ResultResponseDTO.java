package com.example.backend.global.result;

import com.example.backend.global.error.ErrorCodeMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

    public ResultResponseDTO(ErrorCodeMessage errorCodeMessage, Object data) {
        this.status = errorCodeMessage.getStatus();
        this.message = errorCodeMessage.getMessage();
        this.data = data;
    }

    public static ResultResponseDTO of(ResultCodeMessage resultCodeMessage, Object data) {
        return new ResultResponseDTO(resultCodeMessage, data);
    }

   public static ResultResponseDTO of(ResultCodeMessage resultCodeMessage) {
        return new ResultResponseDTO(resultCodeMessage, "");
   }

   public static ResultResponseDTO of(ErrorCodeMessage errorCodeMessage, Object data) {
        return new ResultResponseDTO(errorCodeMessage, data);
   }



}





















