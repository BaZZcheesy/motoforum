import axios from "axios";
import Questions from "../components/Questions";

// Create an instance of Axios with base URL
const instance = axios.create({
    baseURL: "http://localhost:8080/"
});

// Add a request interceptor
instance.interceptors.request.use(
    (config) => {
        // Modify request config here
        const token = localStorage.getItem('jwt_token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
            config.headers['Content-Type'] = 'application/json';
        }
        return config;
    },
    (error) => {
        // Handle request error here
        return Promise.reject(error);
    }
);

// Function to handle answering a question
export const handleAnswer = async (questionId, reply) => {
  try {
    await instance.post("reply/insert", { reply, questionId });
    await loadData(); // Reload questions after submitting an answer
    console.log(instance)
  } catch (ex) {
    console.log(ex);
  }
};

// Function to load all questions
export const loadData = async () => {
  try {
    const response = await instance.get("question/getall");
    return response.data;
  } catch (ex) {
    console.log(ex);
    return [];
  }
};

// Function to handle submitting a new question
export const handleSubmit = async (question) => {
  try {
    await instance.post("question/ask", { question });
    await loadData(); // Reload questions after submitting a new one
  } catch (ex) {
    console.log(ex);
  }
};

export default {
  handleAnswer,
  handleSubmit,
  loadData,
};
