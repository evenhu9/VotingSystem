package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteCreateDto;
import com.example.votingsystem.entity.User;
import com.example.votingsystem.entity.UserVoteRecord;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.repository.UserRepository;
import com.example.votingsystem.repository.UserVoteRecordRepository;
import com.example.votingsystem.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final UserVoteRecordRepository userVoteRecordRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, UserVoteRecordRepository userVoteRecordRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.userVoteRecordRepository = userVoteRecordRepository;
    }

    @Transactional
    public Vote createVote(VoteCreateDto dto, String creatorUsername) {
        if (dto.getStartTime().isAfter(dto.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        Vote vote = new Vote();
        vote.setTitle(dto.getTitle());
        vote.setDescription(dto.getDescription());
        vote.setStartTime(dto.getStartTime());
        vote.setEndTime(dto.getEndTime());
        vote.setCreator(creatorUsername);
        return voteRepository.save(vote);
    }

    @Transactional
    public void cancelVote(Long voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found."));
        if (LocalDateTime.now().isAfter(vote.getStartTime())) {
            throw new IllegalStateException("Cannot cancel a vote that has already started.");
        }
        voteRepository.delete(vote);
    }

    @Transactional
    public Vote updateVoteCount(Long voteId, long newCount) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found."));
        vote.setVoteCount(newCount);
        return voteRepository.save(vote);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public Map<String, Long> getVoteStatistics() {
        return voteRepository.findAll().stream()
                .collect(Collectors.toMap(Vote::getTitle, Vote::getVoteCount));
    }

    @Transactional
    public void castVote(Long voteId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> new IllegalArgumentException("Vote not found."));

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(vote.getStartTime()) || now.isAfter(vote.getEndTime())) {
            throw new IllegalStateException("Voting is not currently active for this item.");
        }

        LocalDate today = LocalDate.now();
        if (userVoteRecordRepository.existsByUserIdAndVoteIdAndVoteDate(user.getId(), voteId, today)) {
            throw new IllegalStateException("You have already voted for this item today.");
        }

        vote.setVoteCount(vote.getVoteCount() + 1);
        voteRepository.save(vote);

        UserVoteRecord record = new UserVoteRecord();
        record.setUserId(user.getId());
        record.setVoteId(voteId);
        record.setVoteDate(today);
        userVoteRecordRepository.save(record);
    }
}