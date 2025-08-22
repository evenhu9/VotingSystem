package com.example.votingsystem.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "voteId", "voteDate"})
})
public class UserVoteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long voteId;

    @Column(nullable = false)
    private LocalDate voteDate;
}