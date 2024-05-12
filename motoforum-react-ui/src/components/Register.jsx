import React, { useState } from "react";
import {Link} from "react-router-dom";
import NavMenu from "./MainNav";
import { useNavigate } from 'react-router-dom';


const baseUrl = "http://localhost:8080/"
const roles = ["user"];

const Register = () => {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleUsernameChange = event => {
        setUsername(event.target.value);
    };

    const handleEmailChange = event => {
        setEmail(event.target.value);
    }

    const handlePasswordChange = event => {
        setPassword(event.target.value)
    }

    const handleSubmit = async (event) => {
        console.log("Sending to server" + username + " " + email + " " + password)
        event.preventDefault();
        try {
            await fetch(baseUrl + "api/auth/signup", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({username, email, password, roles})
            }).then(response => {
                // Improve error handling
                if (!response.status === 200) {
                    alert("Something went wrong")
                }
                alert("User created successfully");
                navigate("/login")
            })
        }
        catch (ex) {
            console.log("Exception occured: " + ex)
        }
    }

    return (
        <>
            <NavMenu />
            <div>
                <h1>Register</h1>
                <form onSubmit={handleSubmit}>
                    <label>Username</label>
                    <input type="text"
                            value={username}
                            onChange={handleUsernameChange}/>
                    <label>EMail</label>
                    <input type="email"
                            value={email} 
                            onChange={handleEmailChange} />
                    <label>Password</label>
                    <input type="text"
                            value={password}
                            onChange={handlePasswordChange} />
                    <button type="submit">Register</button>
                </form>
            </div>
            <Link to={"/login"}>Or login here</Link>
        </ >
    )
};

export default Register;