package com.example.votingsystem.config;

import com.example.votingsystem.entity.User;
import com.example.votingsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumSet;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 检查admin用户是否存在，如果不存在，则认为是首次启动，需要初始化数据
        if (!userRepository.findByUsername("admin").isPresent()) {
            logger.info("首次启动：正在初始化用户数据...");

            // 创建管理员用户
            String adminPassword = generateRandomPassword();
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRoles(EnumSet.of(User.Role.ROLE_ADMIN, User.Role.ROLE_USER));
            admin.setEnabled(true);
            userRepository.save(admin);
            logInitialCredentials("admin", adminPassword);

            // 创建普通用户
            String userPassword = generateRandomPassword();
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode(userPassword));
            user.setRoles(EnumSet.of(User.Role.ROLE_USER));
            user.setEnabled(true);
            userRepository.save(user);
            logInitialCredentials("user", userPassword);

            // 创建访客用户
            String visitorPassword = generateRandomPassword();
            User visitor = new User();
            visitor.setUsername("visitor");
            visitor.setPassword(passwordEncoder.encode(visitorPassword));
            visitor.setRoles(EnumSet.of(User.Role.ROLE_VISITOR));
            visitor.setEnabled(true);
            userRepository.save(visitor);
            logInitialCredentials("visitor", visitorPassword);

        } else {
            logger.info("用户数据已存在，跳过初始化。");
        }
    }

    private String generateRandomPassword() {
        // 生成一个安全的随机密码
        return UUID.randomUUID().toString().substring(0, 16);
    }

    private void logInitialCredentials(String username, String password) {
        logger.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        logger.warn("!!! 初始账户已创建 - 请妥善保管以下凭证 !!!");
        logger.warn("!!! 用户名: {}", username);
        logger.warn("!!! 密码: {}", password);
        logger.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}