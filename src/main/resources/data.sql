INSERT INTO roles(id, role_name) VALUES
(1, 'ROLE_USER');

INSERT INTO users(id, username, email, password) VALUES
(1, 'niae', 'niaofnarnia@example.com', '$2a$12$ujrtJeyCVy992nYx8SJ8i.b0lLycVo9D5beF8/OOWj.pt1uSFpzHq'),
(2, 'goddessbless', 'blessed@example.com', '$2a$12$mr15uTxw.QQUkbeEEO850ekrpIbTUnbuLJv9id/bnxGm4b1cHPuSO'),
(3, 'livinlikenah', 'yes.and@example.com', '$2a$12$cQRHt31sbvaFOsYKMVwZy.C9mhIOCRkfcbJWg4.H/HJnlxQsU7OiC'),
(4, 'catlover', 'kitty@example.com', '$2a$12$nvI3IHRGP7JorOje.D2HMub/Q/32MIsdvWtpaXgmvhy58W1Jv7AJ2'),
(5, 'nunya', 'nunya@example.com', '$2a$12$64jtN/0uZlsqZLFof0iWOONHaJoOc0x08QuMzmP8hYwIEYPRWZtAm');

INSERT INTO user_roles(user_id, role_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1);