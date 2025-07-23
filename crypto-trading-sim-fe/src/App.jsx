import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './components/Register.jsx';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import {useState} from "react";

function App() {
    const [user, setUser] = useState(null);

    return (
        <Router>
            <Routes>
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login onLogin={setUser} />} />
                <Route path="/" element={user ? <Dashboard user={user} onLogOut={setUser} /> : <Login onLogin={setUser} />} />
            </Routes>
        </Router>
    );
}
export default App;