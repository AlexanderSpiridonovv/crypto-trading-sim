import React, { useState } from 'react';
import axios from 'axios';
import {Link} from "react-router-dom";

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const register = async () => {
        try {
            const res = await axios.post("http://localhost:8080/api/users/register", {
                username, password
            });
            setMessage(res.data ? "Registration successful!" : "Username already exists.");
        } catch {
            setMessage("Error occurred during registration.");
        }
    };

    return (
        <div>
            <h2>Register</h2>
            <input placeholder="Username" onChange={e => setUsername(e.target.value)} />
            <input type="password" placeholder="Password" onChange={e => setPassword(e.target.value)} />
            <button onClick={register}>Register</button>
            <p>{message}</p>
            <p style={{ marginTop: "12px" }}>
                Already have an account? <Link to="/login">Login here</Link>
            </p>
        </div>
    );
};

export default Register;
