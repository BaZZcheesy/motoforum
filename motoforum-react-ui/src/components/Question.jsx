import React from 'react';
import api from '../api/api'

function Question({ question }) {
    return (
        <li key={question.id}>
            <p>{question.question}</p>
            <p>Questioner: {question.questioner.username}</p>
            <p>Replies</p>
            <ul>
                <li>
                    {question.replies.map((reply, index) => (
                        <div key={index}>
                            <p>{reply.replyText}</p>
                        </div>
                    ))}

                </li>
            </ul>
            <form onSubmit={(e) => {
                e.preventDefault();
                const reply = prompt("Enter your answer:");
                if (reply !== null) {
                    api.handleAnswer(question.id, reply);
                }
            }}>
                <button type="submit">Answer</button>
            </form>
        </li>
    );
}

export default Question;
