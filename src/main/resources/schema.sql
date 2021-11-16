CREATE TABLE app_user (
	user_id SERIAL PRIMARY KEY NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	user_password VARCHAR(255) NOT NULL,
	status VARCHAR(255) DEFAULT NULL
);

CREATE TABLE auth_role (
  role_id SERIAL PRIMARY KEY NOT NULL,
  role_name varchar(255) DEFAULT NULL,
  role_description varchar(255) DEFAULT NULL
);

INSERT INTO auth_role VALUES (1,'SUPER_USER','This user has ultimate rights for everything');
INSERT INTO auth_role VALUES (2,'ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO auth_role VALUES (3,'APP_USER','This user has access to site, after login - normal user');

CREATE TABLE app_user_auth_role (
	user_id SERIAL NOT NULL,
	role_id SERIAL NOT NULL,
	PRIMARY KEY(user_id, role_id),
	CONSTRAINT FK_app_user FOREIGN KEY (user_id) REFERENCES app_user(user_id),
	CONSTRAINT FK_auth_role FOREIGN KEY (role_id) REFERENCES auth_role(role_id)
);

CREATE TABLE confirmation_token(
	token_id SERIAL PRIMARY KEY NOT NULL,
	confirmation_token VARCHAR(255) DEFAULT NULL,
	created_date TIMESTAMP DEFAULT NULL,
	user_id SERIAL NOT NULL,
	CONSTRAINT FK_app_user FOREIGN KEY (user_id) REFERENCES app_user(user_id)
);