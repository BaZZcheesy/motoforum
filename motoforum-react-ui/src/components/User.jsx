import React from 'react';
import api from '../api/api';
import { useNavigate } from 'react-router-dom';

function User({ user , setUsers }) {

    const navigate = useNavigate();

    const logout = () => {
        localStorage.setItem("jwt_token", "");
        navigate("/")
    }

    return (
        <li key={user.id}>
            <p>{user.username}</p>
            <form onSubmit={async (e) => {
                e.preventDefault();
                const value = prompt("How would you like to be called?")
                const pw = prompt("Please enter your password to confirm:")
                if (value !== null && pw !== null) {
                    const response = await api.updateUserValue(user.id, "username", value, pw);
                    localStorage.setItem("jwt_token", response)
                    const newUser = await api.getUser();
                    setUsers(newUser)
                }
            }}>
                <button type="submit">Change Username</button>
            </form>
            <p>Motorcycle: {user.motorcycle}</p>
            <form onSubmit={async (e) => {
                e.preventDefault();
                const value = prompt("What is your motorcycle?")
                const pw = prompt("Please enter your password to confirm")
                if (value !== null && pw !== null) {
                    const response = await api.updateUserValue(user.id, "motorcycle", value, pw);
                    localStorage.setItem("jwt_token", response)
                    const newUser = await api.getUser();
                    setUsers(newUser)
                }
            }}>
                <button type="submit">Change Username</button>
            </form>
            <ul>
                {user.roles !== undefined && user.roles.map(role =>
                    <li key={role.id}>{role.role}</li>
                )}
            </ul>
            {user.email && <p>Email: {user.email}</p>}
            {user.email &&
            <form onSubmit={async (e) => {
                e.preventDefault();
                const value = prompt("What is your new email?")
                const pw = prompt("Please enter your password to confirm:")
                if (value !== null && pw !== null) {
                    const response = await api.updateUserValue(user.id, "email", value, pw);
                    localStorage.setItem("jwt_token", response)
                    const newUser = await api.getUser();
                    setUsers(newUser)
                }
            }}>
                <button type="submit">Change Username</button>
            </form>}
            {user.password && <p>Your encrypted password: {user.password}</p>}
            {user.password &&
            <form onSubmit={async (e) => {
                e.preventDefault();
                const value = prompt("What is your new password")
                const pw = prompt("Please type your current password to confirm:")
                if (value !== null && pw !== null) {
                    const response = await api.updateUserValue(user.id, "password", value, pw);
                    localStorage.setItem("jwt_token", response)
                    const newUser = await api.getUser();
                    setUsers(newUser)
                }
            }}>
                <button type="submit">Change Username</button>
            </form>}
            <form onSubmit={(e) => {
                e.preventDefault();
                logout()
            }}>
                <button type='submit'>Logout</button>
            </form>
            <form onSubmit={(e) => {
                e.preventDefault();
                api.deleteUser(user.id)
                navigate("/user/me")
            }}>
                <button type='submit'>Delete user account</button>
            </form>
        </li>
    );
}

export default User;
