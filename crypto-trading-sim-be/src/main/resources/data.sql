
-- Insert some cryptocurrencies
INSERT INTO cryptocurrencies (id, symbol, name)
VALUES
    (1, 'BTC/USD', 'Bitcoin'),
    (2, 'ETH/USD', 'Ethereum'),
    (3, 'SOL/USD', 'Solana'),
    (4, 'XRP/USD', 'Ripple'),
    (5, 'ADA/USD', 'Cardano'),
    (6, 'DOGE/USD', 'Dogecoin'),
    (7, 'AVAX/USD', 'Avalanche'),
    (8, 'DOT/USD', 'Polkadot'),
    (9, 'LINK/USD', 'Chainlink'),
    (10, 'TRX/USD', 'Tron'),
    (11, 'MATIC/USD', 'Polygon'),
    (12, 'BCH/USD', 'Bitcoin Cash'),
    (13, 'LTC/USD', 'Litecoin'),
    (14, 'UNI/USD', 'Uniswap'),
    (15, 'ATOM/USD', 'Cosmos'),
    (16, 'ETC/USD', 'Ethereum Classic'),
    (17, 'FIL/USD', 'Filecoin'),
    (18, 'ICP/USD', 'Internet Computer'),
    (19, 'XLM/USD', 'Stellar'),
    (20, 'XMR/USD', 'Monero')
    ON CONFLICT(id) DO NOTHING;