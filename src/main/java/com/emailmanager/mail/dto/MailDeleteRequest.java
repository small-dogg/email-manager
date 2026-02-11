package com.emailmanager.mail.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MailDeleteRequest {

    @NotEmpty(message = "삭제할 메시지 ID 목록은 비어있을 수 없습니다.")
    private List<Integer> messageIds;
}
