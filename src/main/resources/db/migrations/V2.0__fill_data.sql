INSERT INTO t_users (name, date_of_birth, password) VALUES ('Abubakar', '1999-08-20', md5('kukuruza'));
INSERT INTO t_users (name, date_of_birth, password) VALUES ('Artur', '1994-12-31', md5('arbuz777777'));
INSERT INTO t_users (name, date_of_birth, password) VALUES ('Bayden', '1851-08-01', md5('RUSSIANFederation'));


INSERT INTO t_accounts (user_id, balance, initial_balance) VALUES (1, 1743, 1743);
INSERT INTO t_accounts (user_id, balance, initial_balance) VALUES (2, 376, 376);
INSERT INTO t_accounts (user_id, balance, initial_balance) VALUES (3, 100000, 100000);


insert into t_email_data (user_id, email) values (1, 'abubakar@gmail.com');
insert into t_email_data (user_id, email) values (2, 'artur@gmail.com');
insert into t_email_data (user_id, email) values (3, 'bayden@gmail.com');


insert into t_phone_data (user_id, phone) values (1, '89500009999');
insert into t_phone_data (user_id, phone) values (2, '89221118888');
insert into t_phone_data (user_id, phone) values (3, '89128121212');