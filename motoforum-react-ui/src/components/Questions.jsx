import React, { useState, useEffect } from "react";
import Question from './Question'
import api, { loadData } from '../api/api'
import NavMenu from "./MainNav";

const Questions = () => {
    const [question, setQuestion] = useState("");
    const [questions, setQuestions] = useState([]);

    const handleQuestionChange = event => {
        setQuestion(event.target.value)
    }

    useEffect(() => {
        api.loadData().then(data => { setQuestions(data) } )
    }, [])

    return (
        <>
            <NavMenu />
            <div>
                <form onSubmit={async (e) => {
                    e.preventDefault();
                    if (question !== null) {
                        await api.submitQuestion(question);
                        const data = await api.loadData();
                        setQuestion("");
                        setQuestions(data);
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
                    api.loadData().then(data => { setQuestions(data) } )
                }}>
                    Load Questions
                </button>
                
                <h1>Questions</h1>
                <ul>
                    {questions.map(question => (
                        <Question question={question} key={question.id} setQuestions={setQuestions} />
                    ))}
                </ul>
            </div>
        </>
    )
}

export default Questions;