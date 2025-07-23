import React, { useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from "react-router-dom";

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const login = async () => {
        try {
            const res = await axios.post("http://localhost:8080/api/users/login", {
                username, password
            });
            onLogin(res.data);
            navigate('/');
        } catch {
            setError("Invalid credentials");
        }
    };

    return (
        <div>
            <h2>Login</h2>
            <input placeholder="Username" onChange={e => setUsername(e.target.value)} />
            <input type="password" placeholder="Password" onChange={e => setPassword(e.target.value)} />
            <button onClick={login}>Login</button>

            <p>{error}</p>
            <p style={{ marginTop: "12px" }}>
                Don't have an account? <Link to="/register">Register here</Link>
            </p>
        </div>
    );
};

export default Login;
