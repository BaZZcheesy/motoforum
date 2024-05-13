import React, { useState, useEffect } from "react";
import api from "../api/api";
import User from "./User";
import {useParams} from "react-router-dom";
import NavMenu from "./MainNav";


const PublicUsers = () => {
    const [user, setUser] = useState([])
    const {userId} = useParams()

    useEffect(() => {
        api.getUserAccount(userId).then(data => { setUser(data) } )
    }, [])

    return (
        <>
            <NavMenu />
            <div>
                {user !== undefined && <User user={user}/>}
            </div>
        </>
    )
}

export default PublicUsers;