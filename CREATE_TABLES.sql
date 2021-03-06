USE bikestore;
DROP TABLE users;
DROP TABLE articles;
DROP TABLE categories;

CREATE TABLE users (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	login		VARCHAR (20) NOT NULL,
	passwd 		VARCHAR (40) NOT NULL,
	fullName	VARCHAR (255) NOT NULL,
	email		VARCHAR (255) NOT NULL,
	address 	VARCHAR (255) NOT NULL,
	city 		VARCHAR (255) NOT NULL,
	country 	VARCHAR (255) NOT NULL,
	zipcode 	MEDIUMINT UNSIGNED,
	PRIMARY KEY (id),
	UNIQUE KEY (login)
);

CREATE TABLE articles (
	id 			INT UNSIGNED NOT NULL AUTO_INCREMENT,
	brand		VARCHAR (30) NOT NULL,		
	model		VARCHAR (100) NOT NULL,
	description VARCHAR (255) NOT NULL,
	id_category	INT UNSIGNED NOT NULL,
	price		DECIMAL(8,2) NOT NULL,
	stock		INT NOT NULL,
	picture		BLOB,
	PRIMARY KEY (id),
	UNIQUE KEY (brand,model)	
);

CREATE TABLE categories (
	id 			INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR (100) NOT NULL,
	id_cat_superior	INT UNSIGNED,	
	PRIMARY KEY (id),
	UNIQUE KEY (name)
);

select * from users;
select * from categories;

