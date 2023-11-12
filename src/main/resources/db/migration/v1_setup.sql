DROP TABLE IF EXISTS catalog;
CREATE TABLE catalog (
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         itemName TEXT NOT NULL DEFAULT 'No name assigned',
                         itemDescription TEXT NOT NULL DEFAULT 'No description given',
                         isDutch BOOLEAN NOT NULL DEFAULT TRUE,
                         daysToShip INTEGER NOT NULL DEFAULT 3,
                         initialPrice INTEGER NOT NULL DEFAULT -1,
                         auctionEnd TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO catalog (itemName, itemDescription, isDutch, daysToShip, initialPrice, auctionEnd)
VALUES
    ('Vintage Vase', 'A beautiful antique vase.', TRUE, 5, 75, datetime('now', '+3 days')),
    ('Oil Painting', 'Landscape oil painting.', FALSE, 2, 200, datetime('now', '+3 days')),
    ('Silver Necklace', 'Elegant sterling silver jewelry.', TRUE, 4, 50, datetime('now', '+3 days')),
    ('Leather Journal', 'Handcrafted leather-bound journal.', TRUE, 3, 30, datetime('now', '+3 days')),
    ('Wooden Chess Set', 'Handmade walnut chess board with pieces.', FALSE, 7, 125, datetime('now', '+3 days'));


