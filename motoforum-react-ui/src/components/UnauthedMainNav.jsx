import { Link } from 'react-router-dom'

const UnauthedMainNav = () => {
    return (
        <>
            <nav id="main-nav">
                <ul>
                    <li>
                        <Link to="/">Home</Link>
                        <Link to="/login">Login</Link>
                        <Link to="/register">Register</Link>
                    </li>
                </ul>
            </nav>
        </>
    )
}

export default UnauthedMainNav;