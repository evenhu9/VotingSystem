package com.example.votingsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {
    /**
     * 获取当前登录用户的信息。
     * Spring Security 会自动注入 Principal 对象。
     * @param principal 当前登录的用户主体
     * @return 用户信息
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        // 如果 principal 为 null，说明用户未登录
        // Spring Security 默认情况下，如果未登录访问受保护的端点会返回403或重定向，但为了前端明确处理，这里返回principal
        if (principal == null) {
            return ResponseEntity.status(401).body("Not authenticated");
        }
        // 返回 Principal 对象，它通常包含了用户名等基本信息
        return ResponseEntity.ok(principal);
    }
}