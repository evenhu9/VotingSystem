package com.example.votingsystem.controller;

import com.example.votingsystem.aop.AdminOnly;
import com.example.votingsystem.dto.VoteCreateDto;
import com.example.votingsystem.service.IpWhitelistService;
import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final VoteService voteService;
    private final UserService userService;
    private final IpWhitelistService ipWhitelistService;

    public AdminController(VoteService voteService, UserService userService, IpWhitelistService ipWhitelistService) {
        this.voteService = voteService;
        this.userService = userService;
        this.ipWhitelistService = ipWhitelistService;
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