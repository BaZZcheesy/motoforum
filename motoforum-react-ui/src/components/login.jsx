import React, { useState } from 'react'
import {Link} from "react-router-dom";
import { useNavigate } from 'react-router-dom';

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
            await fetch(baseUrl + "api/auth/signin", {
                headers : {
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify(
                    username,
                    password
                )
            }).then( response => {
                if (response.data.accessToken) {
                    localStorage.setItem("jwt_token", response.data.accessToken);
                    localStorage.setItem("user", response.data.username);
                    console.log(response.data)
                    // setUser(response.data.user);
                    navigate("/");
                }
                else {
                    console.log("Login failed")
                }
            })  
        } catch (error) {
            console.error("Login failed:", error);
        }
    };

    return (
        <>
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