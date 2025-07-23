-- USERS: Each user has a virtual account balance
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    balance DECIMAL(18, 2) NOT NULL DEFAULT 10000.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- CRYPTOCURRENCIES: Stores info about top 20 coins
CREATE TABLE IF NOT EXISTS cryptocurrencies (
  id SERIAL PRIMARY KEY,
  symbol VARCHAR(10) UNIQUE NOT NULL,
  name VARCHAR(50) NOT NULL
);

-- HOLDINGS: How much of each coin the user currently holds
CREATE TABLE IF NOT EXISTS holdings (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  crypto_id INTEGER REFERENCES cryptocurrencies(id) ON DELETE CASCADE,
  price_at_transaction DECIMAL(18, 2) NOT NULL,
  quantity DECIMAL(18, 8) NOT NULL CHECK (quantity >= 0),
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TRANSACTIONS: History of buy/sell actions
CREATE TABLE IF NOT EXISTS transactions (
  id SERIAL PRIMARY KEY,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  crypto_id INTEGER REFERENCES cryptocurrencies(id) ON DELETE CASCADE,
  type VARCHAR(4) CHECK (type IN ('BUY', 'SELL')) NOT NULL,
  price_at_transaction DECIMAL(18, 2) NOT NULL,
  quantity DECIMAL(18, 8) NOT NULL,
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  profitable BOOLEAN
);
