package com.example.onlinevotingsystem.entity;

import com.example.onlinevotingsystem.enums.NominationEnums.VoteStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(
        name = "votes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"election_id", "voter_id"}
                )
        }
)
public class Vote {

    @Id
    @GeneratedValue
    private UUID voteId;

    @Column(name = "election_id", nullable = false)
    private UUID electionId;

    @Column(name = "voter_id", nullable = false)
    private String voterId;

    @Column(name = "candidate_id", nullable = false)
    private UUID candidateId;

    @Column(name = "candidate_name", nullable = false)
    private String candidateName;

    @Column(name = "party_name", nullable = false)
    private String partyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_status", nullable = false)
    private VoteStatus voteStatus;

    @Column(name = "voted_at", nullable = false)
    private LocalDateTime votedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}