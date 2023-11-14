DROP TABLE IF EXISTS catalog;
DROP TABLE IF EXISTS bids;
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS users;

-- IDs are not foreign keys because we are going to convert this to microservices -> will use int id reference
CREATE TABLE catalog (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    sellerId INTEGER NOT NULL,
    itemName TEXT NOT NULL,
    itemDescription TEXT NOT NULL DEFAULT 'No description given',
    isDutch BOOLEAN NOT NULL DEFAULT TRUE,
    daysToShip INTEGER NOT NULL,
    shippingPrice DECIMAL(20,2) NOT NULL DEFAULT 1.00,
    expeditePrice DECIMAL(20,2) NOT NULL DEFAULT 2.00,
    initialPrice DECIMAL(20,2) NOT NULL,
    auctionEnd TIMESTAMP NOT NULL,
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE bids (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    bidTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    itemId INTEGER NOT NULL,
    userId INTEGER NOT NULL,
    price DECIMAL(20,2) NOT NULL,

    FOREIGN KEY (userId) REFERENCES Users(id),
    FOREIGN KEY (itemId) REFERENCES Catalog(id)
);

CREATE TABLE payments (
    userId INTEGER PRIMARY KEY,
    cardNumber BIGINT NOT NULL,
    name TEXT NOT NULL,
    expMonth INTEGER NOT NULL,
    expYear INTEGER NOT NULL,
    ccv INTEGER NOT NULL,

    FOREIGN KEY (userId) REFERENCES Users(id)
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    firstName TEXT,
    lastName TEXT,
    streetAddress TEXT,
    streetNumber TEXT,
    postalCode TEXT,
    city TEXT,
    country TEXT
);

--Catalog items (sold by a seller)
INSERT INTO catalog (sellerId, itemName, itemDescription, isDutch, daysToShip, initialPrice, auctionEnd)
VALUES
    (1, 'Vintage Vase', 'A beautiful antique vase.', TRUE, 5, 75.00, datetime('now', '+3 days')),
    (2, 'Oil Painting', 'Landscape oil painting.', FALSE, 2, 200.00, datetime('now', '+3 days'));

--Auction bids (on items by users)
INSERT INTO bids (itemId, userId, price)
VALUES
    (1, 1, 76.00),
    (2, 2, 220.50);

--Users (user: john, pass: test)
INSERT INTO users (username, password, firstName, lastName, streetAddress, streetNumber, postalCode, city, country)
VALUES
    ('john', 'ee26b0dd4af7e749aa1a8ee3c10ae9923f618980772e473f8819a5d4940e0db27ac185f8a0e1d5f84f88bc887fd67b143732c304cc5fa9ad8e6f57f50028a8ff',
     'John', 'Doe', '123 Maple Street', '12', '12345', 'Springfield', 'USA'),
    ('janesmith12', 'securepassword', 'Jane', 'Smith', '456 Oak Avenue', '34', '67890', 'Greenville', 'USA'),
    ('alicejones', 'mypassword', 'Alice', 'Jones', '789 Pine Road', '56', '10112', 'Fairview', 'USA');

--Payment Items (for a bid)
INSERT INTO payments (userId, cardNumber, name, expMonth, expYear, ccv)
VALUES
    ((SELECT id from users WHERE username ='john'), 1234567890123456, 'John Doe', 12, 2023, 123),
    ((SELECT id from users WHERE username ='janesmith12'), 2345678901234567, 'Jane Smith', 6, 2024, 456);
