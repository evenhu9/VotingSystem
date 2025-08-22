-- This script initializes data for the application.
-- Passwords are encrypted using BCrypt.
-- 'admin' -> $2a$10$v7g.DqP6X.fKjOq8sB2d5e/c.5o9hJ2qJ9qY8iM.X.p8sQ.zG5qBq
-- 'user' -> $2a$10$tP6A4B7G.6p.8w.i5qE9c.FzJ9nL2nU2f.b4sK7vY.p2sT.zG3wAa

INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$v7g.DqP6X.fKjOq8sB2d5e/c.5o9hJ2qJ9qY8iM.X.p8sQ.zG5qBq', true);
INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$tP6A4B7G.6p.8w.i5qE9c.FzJ9nL2nU2f.b4sK7vY.p2sT.zG3wAa', true);
INSERT INTO users (username, password, enabled) VALUES ('disabled_user', '$2a$10$tP6A4B7G.6p.8w.i5qE9c.FzJ9nL2nU2f.b4sK7vY.p2sT.zG3wAa', false);

INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO user_roles (user_id, role) VALUES (1, 'ROLE_USER'); -- admin is also a user
INSERT INTO user_roles (user_id, role) VALUES (2, 'ROLE_USER');
INSERT INTO user_roles (user_id, role) VALUES (3, 'ROLE_USER');

-- Initial IP Whitelist
INSERT INTO ip_whitelist (ip_address) VALUES ('127.0.0.1');
INSERT INTO ip_whitelist (ip_address) VALUES ('0:0:0:0:0:0:0:1'); -- for IPv6 localhost