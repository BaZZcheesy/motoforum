import axios from "axios";
import UserDTO from '../dto/UserDto';

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
export const submitAnswer = async (questionId, reply) => {
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
export const submitQuestion = async (question) => {
  try {
    await instance.post("question/ask", { question })
  } catch (ex) {
    console.log(ex);
  }
};

// Function to get a single user account with its id
export const getUserAccount = async (userId) => {
  try {
    const response = await instance.get(`user/byId/${userId}`)
    return response.data
  } catch (ex) {
    console.log(ex)
  }
}

// Function to update a specified value
export const updateUserValue = async (userId, valueToUpdate, property, pw) => {
  try {
    const response = await instance.put(`user/${userId}/${valueToUpdate}`, {property, pw})
    return response.data;
  } catch (ex) {
    console.log(ex)
  }
}

// Get your user
export const getUser = async () => {
  try {
    const response = await instance.get("user/me")
    return response.data
  } catch (ex) {
    console.log(ex)
  }
}

// Delete a question
export const deleteQuestion = async (questionId) => {
  try {
    await instance.delete(`question/delete/${questionId}`)
    await loadData
  } catch (ex) {
    console.log(ex)
  }
}

// Set reply as solution
export const setAsSolution = async (replyId) => {
  try {
    await instance.get(`reply/accept/${replyId}`)
  } catch (ex) {
    console.log(ex)
  }
}

// Delete user
export const deleteUser = async (userId) => {
  
}

// Delete reply
export const deleteReply = async (replyId) => {

}

export default {
  submitAnswer,
  submitQuestion,
  loadData,
  getUserAccount,
  getUser,
  deleteQuestion,
  updateUserValue,
  setAsSolution,
  deleteUser,
  deleteReply
};
