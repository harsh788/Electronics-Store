ALTER TABLE Transactions ADD CONSTRAINT Transactions_fk0 FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID);

ALTER TABLE Product_Transaction ADD CONSTRAINT Product_Transaction_fk0 FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID);

ALTER TABLE Product_Transaction ADD CONSTRAINT Product_Transaction_fk1 FOREIGN KEY (Transaction_D) REFERENCES Transactions(Transaction_ID);