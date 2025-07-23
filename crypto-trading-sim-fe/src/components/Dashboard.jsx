import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import "../App.css";

const CRYPTOS = [
    "BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "ADA/USD",
    "DOGE/USD", "AVAX/USD", "DOT/USD", "LINK/USD", "TRX/USD",
    "MATIC/USD", "BCH/USD", "LTC/USD", "UNI/USD", "ATOM/USD",
    "ETC/USD", "FIL/USD", "ICP/USD", "XLM/USD", "XMR/USD"
];

const Dashboard = ({ user, onLogOut }) => {
    const userId = user.id;
    const [prices, setPrices] = useState({});
    const [quantities, setQuantities] = useState({});
    const [holdings, setHoldings] = useState([]);
    const [transactions, setTransactions] = useState([]);
    const [showTransactions, setShowTransactions] = useState(false);
    const [balance, setBalance] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        fetchHoldings();
        fetchBalance();

        const ws = new WebSocket("wss://ws.kraken.com/v2");
        ws.onopen = () => {
            const payload = {
                method: "subscribe",
                params: {
                    channel: "ticker",
                    symbol: CRYPTOS
                },
                req_id: 1
            };
            ws.send(JSON.stringify(payload));
        };

        ws.onmessage = (event) => {
            const message = JSON.parse(event.data);
            if (message.channel === "ticker" && message.data) {
                const symbol = message.data[0].symbol;
                const price = message.data[0].last;
                if (price) {
                    setPrices(prev => ({ ...prev, [symbol]: price }));
                }
            }
        };

        ws.onerror = (err) => console.error("WebSocket error:", err);
        return () => ws.close();
    }, []);

    const fetchHoldings = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/holdings/${userId}`);
            setHoldings(response.data);
        } catch (err) {
            console.error("Error fetching holdings:", err);
        }
    };

    const fetchBalance = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/users/${userId}/balance`);
            setBalance(response.data);
        } catch (err) {
            console.error("Error fetching balance:", err);
        }
    };

    const fetchTransactions = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/transactions/${userId}`);
            setTransactions(response.data);
            setShowTransactions(true);
        } catch (err) {
            console.error("Error fetching transactions:", err);
            alert("Failed to load transactions");
        }
    };

    const handleQuantityChange = (key, value) => {
        setQuantities(prev => ({ ...prev, [key]: value }));
    };

    const sendTrade = async (type, symbol, holdingId = 0) => {
        const key = type === 'BUY' ? `${symbol}_buy` : `${symbol}_sell_${holdingId}`;
        const quantity = parseFloat(quantities[key]);
        if (!quantity || quantity <= 0) return alert("Invalid quantity");

        try {
            await axios.post(`http://localhost:8080/api/trade/${type.toLowerCase()}`, type === 'BUY' ? {
                userId,
                symbol,
                quantity,
            } : {
                userId,
                symbol,
                holdingId,
                quantity,
            });

            alert(`${type} successful`);
            setQuantities(prev => ({ ...prev, [key]: '' }));
            fetchHoldings();
            fetchBalance();
        } catch (e) {
            alert(`Failed to ${type.toLowerCase()}: ${e.response?.data || e.message}`);
        }
    };

    const handleLogout = () => {
        onLogOut();
        navigate("/login");
    };

    return (
        <div className="container">
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                <h1 className="title">Crypto Trading Simulator</h1>
                <button onClick={handleLogout} className="logout-btn">Logout</button>
            </div>

            <h2 className="section-title">Market Prices</h2>
            <div className="table-wrapper">
                <table>
                    <thead>
                    <tr>
                        <th>Symbol</th>
                        <th>Price</th>
                        <th>Buy Quantity</th>
                        <th>Buy Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {CRYPTOS.map((pair) => (
                        <tr key={pair}>
                            <td>{pair}</td>
                            <td>{prices[pair] || 'Loading...'}</td>
                            <td>
                                <input
                                    type="number"
                                    min="0"
                                    value={quantities[`${pair}_buy`] || ''}
                                    onChange={(e) => handleQuantityChange(`${pair}_buy`, e.target.value)}
                                />
                            </td>
                            <td>
                                <button className="buy-btn" onClick={() => sendTrade('BUY', pair)}>Buy</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <h2 className="account-balance">Your Account Balance: ${balance.toFixed(2)}</h2>

            <h2 className="section-title">Your Holdings</h2>
            <div className="table-wrapper">
                {holdings.length === 0 ? (
                    <p className="no-holdings">No holdings yet.</p>
                ) : (
                    <table>
                        <thead>
                        <tr>
                            <th>Symbol</th>
                            <th>Quantity</th>
                            <th>Sell Quantity</th>
                            <th>Sell Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {holdings.map(({ id, symbol, quantity }) => (
                            <tr key={symbol} className="text-center text-white">
                                <td>{symbol}</td>
                                <td>{quantity}</td>
                                <td>
                                    <input
                                        type="number"
                                        min="0"
                                        value={quantities[`${symbol}_sell_${id}`] || ''}
                                        onChange={(e) => handleQuantityChange(`${symbol}_sell_${id}`, e.target.value)}
                                    />
                                </td>
                                <td>
                                    <button className="sell-btn" onClick={() => sendTrade('SELL', symbol, id)}>Sell</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>

            <div className="button-group">
                <button className="history-btn" onClick={fetchTransactions}>View Transaction History</button>
            </div>

            {showTransactions && (
                <div className="table-wrapper">
                    <h3 className="section-title">Transaction History</h3>
                    <table>
                        <thead>
                        <tr>
                            <th>Symbol</th>
                            <th>Type</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Timestamp</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        {transactions.map(({ id, cryptoSymbol, type, quantity, price, timestamp, profitable }) => (
                            <tr key={id}>
                                <td>{cryptoSymbol}</td>
                                <td>{type}</td>
                                <td>{quantity}</td>
                                <td>${price.toFixed(2)}</td>
                                <td>{new Date(timestamp).toLocaleString()}</td>
                                <td style={{ color: type === 'SELL' ? (profitable ? 'green' : 'red') : 'inherit' }}>
                                    {type === 'SELL' ? (profitable ? '✅ Profit' : '❌ Loss') : ''}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <button
                        className="reset-btn"
                        onClick={async () => {
                            if (window.confirm("Are you sure you want to reset your account? This will delete all holdings and transactions.")) {
                                try {
                                    await axios.post(`http://localhost:8080/api/reset/${userId}`);
                                    alert("Account reset successfully.");
                                    setHoldings([]);
                                    setTransactions([]);
                                    setShowTransactions(false);
                                    fetchBalance();
                                } catch (err) {
                                    alert("Failed to reset account: " + (err.response?.data || err.message));
                                }
                            }
                        }}
                    >
                        Reset Account
                    </button>
                </div>
            )}
        </div>
    );
};

export default Dashboard;
