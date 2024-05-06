import * as React from 'react'
import * as ReactDOM from 'react-dom/client'
import {
    createBrowserRouter, RouterProvider
} from 'react-router-dom'
import App from './components/App'

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />
    }
])

ReactDOM.createRoot(root).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
)