package com.example.votingsystem.repository;

import com.example.votingsystem.entity.UserVoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface UserVoteRecordRepository extends JpaRepository<UserVoteRecord, Long> {
    boolean existsByUserIdAndVoteIdAndVoteDate(Long userId, Long voteId, LocalDate voteDate);
}