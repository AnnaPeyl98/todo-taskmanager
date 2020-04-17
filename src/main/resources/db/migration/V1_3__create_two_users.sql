INSERT INTO users (id, username, password, enabled) VALUES('1ef601de-7508-4f43-a1b4-49062c68c86e', 'admin', '$2a$10$yyvuNKcoFwhTDzYaZhRi2.v72FXhbbeSVrXxIFYXI/6Y9bCov9qUK', TRUE); --admin
INSERT INTO users (id, username, password, enabled) VALUES('c15d3f75-c945-4cc8-b018-cf5525c0dc0c', 'anna', '$2a$10$hespx/Xrk.28lCD7fSP7dewrwps9HK8k3fyvARm8ycM9M7QHzJDfa', TRUE); --utka

INSERT INTO authorities (userId, authority) VALUES('1ef601de-7508-4f43-a1b4-49062c68c86e', 'ADMIN');
INSERT INTO authorities (userId, authority) VALUES('1ef601de-7508-4f43-a1b4-49062c68c86e', 'USER');
INSERT INTO authorities (userId, authority) VALUES('c15d3f75-c945-4cc8-b018-cf5525c0dc0c', 'USER');