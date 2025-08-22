package com.example.votingsystem.controller;

import com.example.votingsystem.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Principal;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    // Accessible by everyone (游客, 普通用户, 管理员)
    @GetMapping
    public ResponseEntity<?> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    // Accessible by authenticated users (普通用户, 管理员)
    @PostMapping("/{id}/vote")
    public ResponseEntity<?> castVote(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("You must be logged in to vote.");
        }
        try {
            voteService.castVote(id, principal.getName());
            return ResponseEntity.ok("Vote cast successfully for vote " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Accessible by everyone to get a shareable link
    @GetMapping("/{id}/share")
    public ResponseEntity<String> getShareLink(@PathVariable Long id) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String baseUrl = attr.getRequest().getRequestURL().toString().replace(attr.getRequest().getRequestURI(), "");
        String shareLink = baseUrl + "/api/votes?voteId=" + id; // A simple shareable link
        return ResponseEntity.ok(shareLink);
    }
}