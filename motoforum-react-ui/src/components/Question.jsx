import React from 'react';
import api from '../api/api'
import { Link } from 'react-router-dom'

// TODO Fix answer button rendering

function Question({ question, setQuestions }) {

    const userUrl = `/user/byId/${question.questioner.id}`

    return (
        <li key={question.id}>
            <p>{question.question}</p>
            <p>Questioner: <Link to={userUrl}>{question.questioner.username}</Link></p>
            <p>Replies</p>
            <ul>
                <li>
                    {question.replies.map((reply) => (
                        <div key={reply.id}>
                            <p>{reply.replyText}</p>
                            {question.solved === false &&
                                <form onSubmit={async (e) => {
                                    e.preventDefault();
                                    console.log(reply.id)
                                    await api.setAsSolution(reply.id);
                                    const data = await api.loadData();
                                    setQuestions(data);
                                }}>
                                    <button type='submit'>Set as solution</button>
                                </form>
                            }
                            {reply.solution === true &&
                                <p>Solution</p>
                            }
                            <form onSubmit={async (e) => {
                                e.preventDefault();
                                console.log(reply.id)
                                await api.deleteReply(reply.id)
                                const data = await api.loadData();
                                setQuestions(data);
                            }}>
                                <button type='submit'>Delete reply</button>
                            </form>
                        </div>
                    ))}

                </li>
            </ul>
            {question.solved === false &&
                <form onSubmit={async (e) => {
                    e.preventDefault();
                    const reply = prompt("Enter your answer:");
                    if (reply !== null) {
                        await api.submitAnswer(question.id, reply);
                        const data = await api.loadData();
                        setQuestions(data);
                    }
                }}>
                    <button type="submit">Answer</button>
                </form>
            }

            <form onSubmit={async (e) => {
                e.preventDefault();
                await api.deleteQuestion(question.id);
                const data = await api.loadData();
                setQuestions(data);
            }}>
                <button type="submit">Delete Question</button>
            </form>
        </li>
    );
}

export default Question;
