
INSERT INTO book(bookName, ISBN, price, description) VALUES
('Java', 'ABC#13', 1000, 'This book is for java1');


INSERT INTO book(bookName, ISBN, price, description) VALUES
('Java2', 'EFC#1', 2000, 'This book is for java2');


INSERT INTO book(bookName, ISBN, price, description) VALUES
('Java3', 'GHI#3', 3000, 'This book is for java3');


INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('aniket@gmail.com',
'$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('sharma@gmail.com',
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_GUEST');

INSERT INTO user_role (userId, roleId)
VALUES (1, 1);
INSERT INTO user_role (userId, roleId)
VALUES (2, 2);