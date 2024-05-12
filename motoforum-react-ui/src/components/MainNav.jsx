import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'

const NavMenu = () => {



    return (
        <>
            <nav id="main-nav">
                <ul>
                    <li>
                        <Link to="/">Home</Link>
                        <Link to="/questions">Questions</Link>
                        <Link to="/login">Login</Link>
                        <Link to="/register">Register</Link>
                        <Link to="/user/me">Your Account</Link>
                    </li>
                </ul>
            </nav>
        </>
    )
}

export default NavMenu;