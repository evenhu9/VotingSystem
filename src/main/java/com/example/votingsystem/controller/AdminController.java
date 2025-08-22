package com.example.votingsystem.controller;

import com.example.votingsystem.aop.AdminOnly;
import com.example.votingsystem.dto.VoteCreateDto;
import com.example.votingsystem.entity.User; // 导入 User 实体
import com.example.votingsystem.repository.UserRepository; // 导入 UserRepository
import com.example.votingsystem.service.IpWhitelistService;
import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List; // 导入 List

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final VoteService voteService;
    private final UserService userService;
    private final IpWhitelistService ipWhitelistService;
    private final UserRepository userRepository; // 新增 UserRepository 依赖

    // 修改构造函数以注入 UserRepository
    public AdminController(VoteService voteService, UserService userService, IpWhitelistService ipWhitelistService, UserRepository userRepository) {
        this.voteService = voteService;
        this.userService = userService;
        this.ipWhitelistService = ipWhitelistService;
        this.userRepository = userRepository; // 初始化
    }

    // --- Vote Management ---
    @PostMapping("/votes")
    @AdminOnly
    public ResponseEntity<?> createVote(@RequestBody VoteCreateDto dto, Principal principal) {
        try {
            return ResponseEntity.ok(voteService.createVote(dto, principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/votes/{id}")
    @AdminOnly
    public ResponseEntity<?> cancelVote(@PathVariable Long id) {
        try {
            voteService.cancelVote(id);
            return ResponseEntity.ok("Vote " + id + " cancelled successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/votes/{id}/count")
    @AdminOnly
    public ResponseEntity<?> updateVoteCount(@PathVariable Long id, @RequestParam long count) {
        try {
            return ResponseEntity.ok(voteService.updateVoteCount(id, count));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/votes/stats")
    @AdminOnly
    public ResponseEntity<?> getVoteStats() {
        return ResponseEntity.ok(voteService.getVoteStatistics());
    }

    // --- User Management ---

    /**
     * 新增：获取所有用户列表的接口
     */
    @GetMapping("/users")
    @AdminOnly
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("/users/{id}/disable")
    @AdminOnly
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        try {
            userService.disableUser(id);
            return ResponseEntity.ok("User " + id + " disabled.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/users/{id}/enable")
    @AdminOnly
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
        try {
            userService.enableUser(id);
            return ResponseEntity.ok("User " + id + " enabled.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --- IP Whitelist Management ---
    @GetMapping("/ip-whitelist")
    @AdminOnly
    public ResponseEntity<?> getIpWhitelist() {
        return ResponseEntity.ok(ipWhitelistService.getAll());
    }

    @PostMapping("/ip-whitelist")
    @AdminOnly
    public ResponseEntity<?> addIpToWhitelist(@RequestParam String ip) {
        ipWhitelistService.addIp(ip);
        return ResponseEntity.ok("IP " + ip + " added to whitelist.");
    }
}