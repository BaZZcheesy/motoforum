import React, { useState, useEffect } from "react";

const baseUrl = "http://localhost:8080/";

let token = localStorage.getItem("jwt_token");

const Questions = () => {
    const [question, setQuestion] = useState("")
    const [questions, setQuestions] = useState("")

    const handleQuestionChange = event => {
        setQuestion(event.target.value)
    }

    const loadData = async () => {
        try {
            await fetch(baseUrl + "question/get", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(token)
            }).then( response => {
                setQuestions(response)
            })
        }
        catch (ex) {
            console.log(ex)
        }
    } 

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await fetch(baseUrl + "question/ask", {
                method: "POST",
                headers : {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({question, token})
            })
            console.log(token)
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
                    <input type="text" value={question} onChange={handleQuestionChange} />
                    <button type="submit">Send question</button>
                </form>
            </div>
            <div>
                <button onClick={loadData}></button>
                <h1 value={questions}></h1>
            </div>
        </>
    )
}

export default Questions;