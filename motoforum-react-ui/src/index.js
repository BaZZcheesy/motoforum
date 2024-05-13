import * as React from 'react'
import * as ReactDOM from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import App from './components/App'
import Login from './components/login'
import Register from './components/Register'
import Questions from './components/Questions'
import Users from './components/Users'
import PublicUsers from './components/PublicUsers'
import Welcome from './components/Welcome'

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />
    },
    {
        path: "/login",
        element: <Login />
    },
    {
        path: "/register",
        element: <Register />
    },
    {
        path: "/questions",
        element: <Questions />
    },
    {
        path: "/user/byId/:userId",
        element: <PublicUsers />
    },
    {
        path: "/user/me",
        element: <Users />
    },
    {
        path: "/home",
        element: <Welcome />
    }
])

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
)