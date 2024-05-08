import React, { useState } from "react";

const baseUrl = "http://localhost:8080/"

const Questions = () => {
    const [question, setQuestion] = useState("")

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await fetch(baseUrl + "question", {
                method: "POST",
                headers : {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(question, localStorage.getItem("jwt_token"))
            })
        }
        catch (ex) {
            console.log(ex);
        }
    }

    return(
        <>
            <div>
                <form onSubmit={handleSubmit}>
                    <label>Ask your question</label>
                    <input type="text" value={question} onChange={setQuestion} />
                    <button type="submit">Send question</button>
                </form>
            </div>
        </>
    )
}

export default Questions;