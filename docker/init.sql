CREATE DATABASE IF NOT EXISTS cards;
USE cards;

CREATE TABLE users (
  user_id int NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  password varchar(64) NOT NULL,
  PRIMARY KEY (user_id)
);

CREATE TABLE roles (
  role_id int NOT NULL,
  name varchar(45) NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE users_roles (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  KEY user_fk_idx (user_id),
  KEY role_fk_idx (role_id),
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
);

INSERT INTO roles (role_id, name) VALUES (1, 'Admin');
INSERT INTO roles (role_id, name) VALUES (2, 'Member');

INSERT INTO users (username, password) VALUES ('admin@example.com', '$2a$12$NXVa0Q8BKVykFfEh56j6MeOlhNeAE2uFoBhrYtjHfrKj4b9ZXSSeK');
INSERT INTO users (username, password) VALUES ('user2@example.com', '$2a$12$UECnUZoGqm6oAFoDeAff1Op6a1efzWs0rd.jZ4pTJXaF9zjh3byPO');
INSERT INTO users (username, password) VALUES ('user3@example.com', '$2a$12$oVjOinJ0HvVjboBAdrRhsOVkJd9fnfirMGeQeiZUnfZnvOXELJiU2');
INSERT INTO users (username, password) VALUES ('user4@example.com', '$2a$12$nqyAK3nHMGxqqyxTW9iP7eErqKOaN1zQ4zcohgvaHs671Hf0PfXUG');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);

CREATE TABLE cards (
    id int NOT NULL AUTO_INCREMENT,
    name  varchar(50) NOT NULL,
    description varchar(50),
    status varchar(50),
    color varchar(10),
    created_at DATE NOT NULL,
    user_id int,
    PRIMARY KEY (ID)
);

CREATE UNIQUE INDEX idx_card_name  ON cards (name);

INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card1','Card 1', 'To Do','#FF0000', CURRENT_DATE(), 1);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card2','Card 2', 'To Do','#00FF00', CURRENT_DATE(), 1);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card3','Card 3', 'To Do','#DC01D1', CURRENT_DATE(), 2);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card4','Card 4', 'To Do','#DC01D1', CURRENT_DATE(), 2);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card5','Card 5', 'To Do','#3FB258', CURRENT_DATE(), 2);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card6','Card 6', 'To Do','#CA6FB9', CURRENT_DATE(), 2);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card7','Card 7', 'To Do','#CFB623', CURRENT_DATE(), 3);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card8','Card 8', 'To Do','#DC01D1', CURRENT_DATE(), 3);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card9','Card 9', 'To Do','#97D047', CURRENT_DATE(), 3);
INSERT INTO cards (name, description, status, color, created_at, user_id) VALUES ('card10','Card 10', 'To Do','#70608F', CURRENT_DATE(), 4);