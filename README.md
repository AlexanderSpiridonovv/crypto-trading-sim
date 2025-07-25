# Cryptocurrency Trading Simulator

A full-stack web application that simulates cryptocurrency trading in real-time. Users can register, log in, buy and sell cryptocurrencies, track their live holdings and view their transactions.

---

## 📁 Project Structure


### Backend (Spring Boot)
- `model/` – Entities like `User`, `Transaction`, `Holding`, `Cryptocurrency`
- `dto/` – Data transfer objects
- `controller/` – API endpoints for user actions and trading logic
- `service/` – Business logic layer
- `repository/` – Direct SQL access using `JdbcTemplate`
- `websocket/` – WebSocket configuration
- `util/` – Hash utils
- `config/` – CORS configuration
- `schema.sql` – DDL script
- `data.sql` – Initial cryptocurrencies


### Frontend (React)
- `App.jsx` – Main application logic
- `Login.jsx`, `Register.jsx` – Auth pages
- `Dashboard.jsx` – Trade and portfolio overview

## ⚙️ Technologies Used

### Backend
- Java 17
- Spring Boot
- PostgreSQL
- JDBC (no ORM)
- Kraken WebSocket API (real-time crypto prices)
- SHA-256 password hashing (`MessageDigest`)

### Frontend
- React.js
- Axios
- React Router DOM

---

## 🧠 Features

- 🔐 User authentication (register, login, logout)
- 📈 Real-time crypto prices via Kraken WebSocket
- 💰 Buying and selling of cryptocurrencies (Demo account)
- 📊 Per-user holdings, transaction history, balance, reset
- 🔄 React frontend with conditional rendering and routing



## 🗂️ `schema.sql`

Defines the PostgreSQL database structure. Located at:

`backend/src/main/resources/schema.sql`


---

## 🛠️ How to Run the Project

### 📂 1. Clone the Repository

```bash
git clone https://github.com/AlexanderSpiridonovv/crypto-trading-sim.git
cd crypto-trading-simulator
```

### 🐘 2. Set Up PostgreSQL

Install PostgreSQL and create a database:

```sql
CREATE DATABASE crypto_sim;
```

ℹ️ `schema.sql` and `data.sql` are automatically executed on app startup and populate cryptocurrencies.

### 🔙 3. Configure and Start the Backend

Edit `application.properties`:

```properties
# backend/src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/crypto_sim
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Start the backend (from the `crypto-trading-sim-be/` directory):

```bash
./mvnw spring-boot:run
```

Or run the main class from your IDE (e.g., IntelliJ).

### 🌐 4. Start the Frontend

From the `crypto-trading-sim-fe/` directory:

```bash
npm install
npm run dev
```

Access the app at: [http://localhost:5173](http://localhost:5173)

---

## 🔐 Some API Endpoints (Spring Boot)

### User
- `POST /api/users/register` – Register a new user
- `POST /api/users/login` – Login with username and password

### Trade
- `POST /api/trade/buy` – Buy crypto
- `POST /api/trade/sell` – Sell crypto

### Price
- Real-time WebSocket integration with Kraken for BTC, ETH, SOL, etc.

---

## 🚀 Future Improvements

- Update the UI
- JWT-based authentication and session management
- Docker support (Dockerfile + docker-compose)
- RESTful pagination for holdings and transactions
- Admin dashboard and analytics
- Email verification and password reset

---

## 📜 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

Alexander Spiridonov
