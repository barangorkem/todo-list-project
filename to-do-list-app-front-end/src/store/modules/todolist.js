import axios from 'axios';
// import {BASE_URL} from '../../util/path';

const state = {
    todoLists: []
};

const getters = {
    allTodoLists: (state) => state.todoLists
};

const actions = {
    getTodoLists({commit}) {
        axios.get(`/api/todolist/getTodoLists`).then((response) => {
            commit('setTodoLists', response.data.payload);
        }).catch(err => {
            throw err;
        });
    },
    postTodoList({commit},todoListData) {
        return axios.post(`/api/todolist/postTodoList`,todoListData); 
    },
    deleteTodoList({commit},todoListId) {
        return axios.delete(`/api/todolist/deleteTodoList/${todoListId}`); 
    }
};

const mutations = {
    setTodoLists: (state, todoLists) => (state.todoLists = todoLists)
};

export default {state, getters, actions, mutations}
