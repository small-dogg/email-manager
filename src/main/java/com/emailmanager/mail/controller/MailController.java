package com.emailmanager.mail.controller;

import com.emailmanager.common.dto.ApiResponse;
import com.emailmanager.config.MailServerProperties.ServerConfig;
import com.emailmanager.mail.dto.MailDeleteRequest;
import com.emailmanager.mail.dto.MailDetailResponse;
import com.emailmanager.mail.dto.MailPageResponse;
import com.emailmanager.mail.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/servers")
    public ResponseEntity<ApiResponse<List<ServerConfig>>> getServers() {
        return ResponseEntity.ok(ApiResponse.success(mailService.getServers()));
    }

    @GetMapping("/{serverName}/messages")
    public ResponseEntity<ApiResponse<MailPageResponse>> getMessages(
            @PathVariable String serverName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "desc") String sort) {
        return ResponseEntity.ok(ApiResponse.success(mailService.getMessages(serverName, page, size, sort)));
    }

    @GetMapping("/{serverName}/messages/{messageId}")
    public ResponseEntity<ApiResponse<MailDetailResponse>> getMessage(
            @PathVariable String serverName,
            @PathVariable int messageId) {
        return ResponseEntity.ok(ApiResponse.success(mailService.getMessage(serverName, messageId)));
    }

    @DeleteMapping("/{serverName}/messages")
    public ResponseEntity<ApiResponse<Void>> deleteMessages(
            @PathVariable String serverName,
            @Valid @RequestBody MailDeleteRequest request) {
        mailService.deleteMessages(serverName, request.getMessageIds());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
