CREATE database electronics_store;

USE electronics_store;

CREATE TABLE Customer (
	Customer_ID INT NOT NULL AUTO_INCREMENT,
	Fname varchar(30) NOT NULL,
	Lname varchar(30) NOT NULL,
	Mobile_number BIGINT NOT NULL UNIQUE,
	Email_ID varchar(255) UNIQUE,
	Address varchar(255) NOT NULL,
	PRIMARY KEY (Customer_ID)
);

CREATE TABLE Product (
	Product_ID INT NOT NULL AUTO_INCREMENT,
	Product_type ENUM('smartphone', 'television', 'AC', 'Refrigerator') NOT NULL,
	Brand varchar(30) NOT NULL,
	Price BIGINT NOT NULL,
	Units_left BIGINT,
	PRIMARY KEY (Product_ID)
);

CREATE TABLE Transactions (
	Transaction_ID BIGINT NOT NULL AUTO_INCREMENT,
	Customer_ID INT NOT NULL,
	Transaction_date DATETIME NOT NULL DEFAULT NOW(),
	Total_price INT NOT NULL,
	PRIMARY KEY (Transaction_ID)
);

CREATE TABLE Product_Transaction (
	PT_ID INT NOT NULL AUTO_INCREMENT,
	Product_ID INT NOT NULL,
	Transaction_D INT NOT NULL,
	Quantity INT NOT NULL DEFAULT 1,
	Cost INT NOT NULL,
	PRIMARY KEY (PT_ID)
);
