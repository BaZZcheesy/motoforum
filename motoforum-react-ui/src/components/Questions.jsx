import React, { useState, useEffect } from "react";
import axios from "axios";

const baseUrl = "http://localhost:8080/";

let token = localStorage.getItem("jwt_token");

const Questions = () => {
    const [question, setQuestion] = useState("");
    const [questions, setQuestions] = useState([]);

    const handleQuestionChange = event => {
        setQuestion(event.target.value)
    }

    const loadData = async () => {
        try {
            const response = await axios.post(baseUrl + "question/get", { token }, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            setQuestions(response.data);
        } catch (ex) {
            console.log(ex);
        }
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await axios.post(baseUrl + "question/ask", { question, token }, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            loadData(); // Reload questions after submitting a new one
        } catch (ex) {
            console.log(ex);
        }
    }

    const handleAnswer = async (questionId, reply) => {
        try {
            await axios.post(baseUrl + "reply/insert", { reply, token, questionId }, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            loadData(); // Reload questions after submitting an answer
        } catch (ex) {
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
                <h1>Questions</h1>
                <ul>
                    {questions.map(question => (
                        <li key={question.id}>
                            <p>{question.question}</p>
                            {/*<p>Questioner: {question.questioner.username}</p>*/}
                            {/* Add rendering for replies if needed */}
                            <form onSubmit={(e) => {
                                e.preventDefault();
                                const reply = prompt("Enter your answer:");
                                if (reply !== null) {
                                    handleAnswer(question.id, reply);
                                }
                            }}>
                                <button type="submit">Answer</button>
                            </form>
                        </li>
                    ))}
                </ul>
            </div>
        </>
    )
}

export default Questions;