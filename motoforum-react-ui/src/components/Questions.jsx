import React, { useState, useEffect } from "react";
import Question from './Question'
import api from '../api/api'

const Questions = () => {
    const [question, setQuestion] = useState("");
    const [questions, setQuestions] = useState([]);

    const handleQuestionChange = event => {
        setQuestion(event.target.value)
    }

    return (
        <>
            <div>
                <form onSubmit={(e) => {
                    e.preventDefault();
                    if (question !== null) {
                        api.handleSubmit(question).then(api.loadData()).then(data => {setQuestions(data)});
                    }
                }}>
                    <label>Ask your question</label>
                    <input type="text" value={question} onChange={handleQuestionChange} />
                    <button type="submit">Send question</button>
                </form>
            </div>
            <div>
                <button onClick={(e) => {
                    e.preventDefault();
                    api.loadData().then(data => {setQuestions(data)})
                    }}></button>
                <h1>Questions</h1>
                <ul>
                    {questions.map(question => (
                        <Question question={question} />
                    ))}
                </ul>
            </div>
        </>
    )
}

export default Questions;