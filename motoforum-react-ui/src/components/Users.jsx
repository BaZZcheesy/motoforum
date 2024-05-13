import React, { useState, useEffect } from "react";
import api from "../api/api";
import User from "./User";
import NavMenu from "./MainNav";


const Users = () => {
    const [user, setUser] = useState([])

    useEffect(() => {
        api.getUser().then(data => { setUser(data) } )
    }, [])

    return (
        <>
            <NavMenu />
            <div>
                {user !== undefined && <User user={user} setUsers={setUser}/>}
            </div>
        </>
    )
}

export default Users;