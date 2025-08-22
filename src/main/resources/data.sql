-- This script initializes data for the application.
-- Passwords are encrypted using BCrypt.
-- 'hyw_security@' -> $2a$10$rK8CubYH0Pep.BPEmnO32uUvlsZ1Aat3Ibnoj6LwryyUqSvtzDpD.
-- 'hyw_security*' -> $2a$10$l1ZDQFViYF.eGjqXqhg.Xun2N.lKexvPYK8NmwXHXFqqBKa8k5.tO
-- 'hyw_security#' -> $2a$10$aPzEXo799B7L4ZhjJ.YJqup4EjJXFJsbsLB3si.vZz5TyZ73VALp2

INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$rK8CubYH0Pep.BPEmnO32uUvlsZ1Aat3Ibnoj6LwryyUqSvtzDpD.', true);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$l1ZDQFViYF.eGjqXqhg.Xun2N.lKexvPYK8NmwXHXFqqBKa8k5.tO', true);
INSERT INTO users (username, password, enabled) VALUES ('visitor', '$2a$10$aPzEXo799B7L4ZhjJ.YJqup4EjJXFJsbsLB3si.vZz5TyZ73VALp2', true);

INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_USER'); -- admin is also a user
INSERT INTO user_roles (user_id, role) VALUES (2, 'ROLE_USER');
INSERT INTO user_roles (user_id, role) VALUES (3, 'ROLE_VISITOR');

-- Initial IP Whitelist
INSERT INTO ip_whitelist (ip_address) VALUES ('127.0.0.1');
INSERT INTO ip_whitelist (ip_address) VALUES ('0:0:0:0:0:0:0:1'); -- for IPv6 localhost