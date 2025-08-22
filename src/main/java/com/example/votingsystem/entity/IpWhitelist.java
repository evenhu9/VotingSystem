package com.example.votingsystem.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class IpWhitelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ipAddress;
}