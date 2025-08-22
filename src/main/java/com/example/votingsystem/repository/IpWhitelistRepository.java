package com.example.votingsystem.repository;

import com.example.votingsystem.entity.IpWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpWhitelistRepository extends JpaRepository<IpWhitelist, Long> {
    boolean existsByIpAddress(String ipAddress);
}