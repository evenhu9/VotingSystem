package com.example.votingsystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VoteCreateDto {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}