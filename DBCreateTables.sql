CREATE DATABASE 'SpringLoginReg2' CHARACTER SET utf8;

CREATE SEQUENCE user_id_sequence;

CREATE TABLE account (
pk_user_id INTEGER NOT NULL DEFAULT nextval('user_id_sequence'),
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
user_password_hash VARCHAR(255) NOT NULL,
status VARCHAR(255),
password_length INTEGER NOT NULL,
PRIMARY KEY(pk_user_id)
);

CREATE SEQUENCE refresh_token_sequence;

CREATE TABLE refresh_token (
pk_refresh_token_id INTEGER NOT NULL DEFAULT nextval('refresh_token_sequence'),
fk_account_id INTEGER NOT NULL,
token VARCHAR(255),
expires TIMESTAMP NOT NULL,
created TIMESTAMP NOT NULL,
created_by_ip VARCHAR(255),
revoked TIMESTAMP,
revoked_by_ip VARCHAR(255),
replaced_by_token VARCHAR(255),
PRIMARY KEY(pk_refresh_token_id),
CONSTRAINT fk_account_id FOREIGN KEY (fk_account_id) REFERENCES account (pk_account_id) ON UPDATE CASCADE
);

INSERT INTO account (
title,
first_name,
last_name,
email,
password_hash,
accept_terms BOOLEAN NOT NULL,
role INTEGER NOT NULL,
verification_token,
verified,
reset_token,
reset_token_expires TIMESTAMP,
password_reset TIMESTAMP,
created TIMESTAMP NOT NULL,
updated TIMESTAMP,
) VALUES (
'Dr.', 'Martin', 'Obermayer', 'mo@gmail.at', 123123, true, 'Admin', 
);







CREATE DATABASE 'AspNgPostJwtEmailAuth' CHARACTER SET utf8;

CREATE SEQUENCE account_sequence;

CREATE TABLE account (
pk_account_id INTEGER NOT NULL DEFAULT nextval('account_sequence'),
title VARCHAR(255),
first_name VARCHAR(255),
last_name VARCHAR(255),
email VARCHAR(255),
password_hash VARCHAR(255),
accept_terms BOOLEAN NOT NULL,
role INTEGER NOT NULL,
verification_token VARCHAR(255),
verified TIMESTAMP,
reset_token VARCHAR(255),
reset_token_expires TIMESTAMP,
password_reset TIMESTAMP,
created TIMESTAMP NOT NULL,
updated TIMESTAMP,
PRIMARY KEY(pk_account_id)
);

CREATE SEQUENCE refresh_token_sequence;

CREATE TABLE refresh_token (
pk_refresh_token_id INTEGER NOT NULL DEFAULT nextval('refresh_token_sequence'),
fk_account_id INTEGER NOT NULL,
token VARCHAR(255),
expires TIMESTAMP NOT NULL,
created TIMESTAMP NOT NULL,
created_by_ip VARCHAR(255),
revoked TIMESTAMP,
revoked_by_ip VARCHAR(255),
replaced_by_token VARCHAR(255),
PRIMARY KEY(pk_refresh_token_id),
CONSTRAINT fk_account_id FOREIGN KEY (fk_account_id) REFERENCES account (pk_account_id) ON UPDATE CASCADE
);

INSERT INTO account (
title,
first_name,
last_name,
email,
password_hash,
accept_terms BOOLEAN NOT NULL,
role INTEGER NOT NULL,
verification_token,
verified,
reset_token,
reset_token_expires TIMESTAMP,
password_reset TIMESTAMP,
created TIMESTAMP NOT NULL,
updated TIMESTAMP,
) VALUES (
'Dr.', 'Martin', 'Obermayer', 'mo@gmail.at', 123123, true, 'Admin', 
);

id: 1, username: 'admin', password: 'admin', firstName: 'Admin', lastName: 'User', role: Role.Admin 