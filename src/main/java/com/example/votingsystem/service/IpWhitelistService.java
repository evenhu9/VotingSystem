package com.example.votingsystem.service;

import com.example.votingsystem.entity.IpWhitelist;
import com.example.votingsystem.repository.IpWhitelistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IpWhitelistService {

    private final IpWhitelistRepository ipWhitelistRepository;

    public IpWhitelistService(IpWhitelistRepository ipWhitelistRepository) {
        this.ipWhitelistRepository = ipWhitelistRepository;
    }

    public boolean isAllowed(String ipAddress) {
        return ipWhitelistRepository.existsByIpAddress(ipAddress);
    }

    @Transactional
    public void addIp(String ipAddress) {
        if (!isAllowed(ipAddress)) {
            IpWhitelist ip = new IpWhitelist();
            ip.setIpAddress(ipAddress);
            ipWhitelistRepository.save(ip);
        }
    }

    public List<IpWhitelist> getAll() {
        return ipWhitelistRepository.findAll();
    }
}