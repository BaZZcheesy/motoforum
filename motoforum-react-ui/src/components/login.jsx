import React, { useState } from 'react'
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import UnauthedNavMenu from './UnauthedMainNav';


const baseUrl = "http://localhost:8080/"

const Login = () => {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleUsernameChange = event => {
        setUsername(event.target.value)
    }

    const handlePasswordChange = event => {
        setPassword(event.target.value)
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(baseUrl + "api/auth/signin", {
                username,
                password
            });
            if (response.data.accessToken) {
                localStorage.setItem("jwt_token", response.data.accessToken);
                navigate('/questions');
            }
        } catch (error) {
            console.error("Login failed:", error);
        }
    };

    return (
        <>
            <UnauthedNavMenu />
            <div>
                <h1>Login</h1>
                <form onSubmit={handleSubmit}>
                    <label>Username</label>
                    <input type="text"
                            value={username}
                            onChange={handleUsernameChange}/>
                    <label>Password</label>
                    <input type="text" 
                            value={password}
                            onChange={handlePasswordChange}/>
                    <button type="submit">Login</button>
                </form>
            </div>
            <Link to={"/register"}>Or register here</Link>
        </ >
    )
};

export default Login;