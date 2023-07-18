INSERT INTO Customer(Fname, Lname, Mobile_number, Email_ID, Address) VALUES 
('John', 'Smith', 7583469487, 'John.Smith@gmail.com', '731 Fondren, Houston, TX'),
('Franklin', 'Wong', 8736491274, 'Franklin.Wong@gmail.com', '638 Fondren, Houston, TX'),
('Alicia', 'Zelaya', 4325847298, 'Alicia.Zelaya@gmail.com', '3321 Fondren, Houston, TX');

INSERT INTO Product(Product_type, Brand, Price, Units_left) VALUES
('smartphone', 'Apple', 120000, 100),
('smartphone', 'Samsung', 65000, 250),
('television', 'Samsung', 35990, 50),
('television', 'LG', 20999, 60),
('AC', 'Mitsubishi', 60000, 80),
('AC', 'Videocon', 35000, 45),
('Refrigerator', 'Samsung', 20000, 20),
('Refrigerator', 'Whirlpool', 25000, 30);

INSERT INTO Transactions(Customer_ID, Total_price) VALUES
(1, 100990),
(2, 40999),
(3, 60000),
(3, 240000);

INSERT INTO Product_Transaction(Product_ID, Transaction_D, Quantity, Cost) VALUES
(2, 1, 1, 65000),
(3, 1, 1, 35999),
(4, 2, 1, 20999),
(6, 2, 1, 20000),
(5, 3, 1, 60000),
(1, 4, 2, 120000);