import axios from 'axios';
// import {BASE_URL} from '../../util/path';

const state = {
    todoListItems: [],
    todoListItem:{}
};

const getters = {
    allTodoListItems: (state) => state.todoListItems,
    todoListItem: (state) => state.todoListItem
};

const actions = {
    getTodoListItems({commit},todoListId) {
        axios.get(`/api/todolistitem/getTodoListItems/${todoListId}`).then((response) => {
            console.log("res",response);
             commit('setTodoListItems', response.data.payload);
        })
    },
    getTodoListItemsByFilter({commit},todoListFilterData) {
        console.log
        axios.get(`/api/todolistitem/getTodoListItemsFilter/${todoListFilterData.todoListId}?filterId=${todoListFilterData.filterId}`).then((response) => {
            console.log("res",response);
             commit('setTodoListItems', response.data.payload);
        })
    },
    getTodoListItemsBySliceItemId({commit},itemId) {
       
             commit('setTodoListItemsBySliceItemId', itemId);
       
    },
    postTodoListItem({commit},todoListItem) {
        return axios.post(`/api/todolistitem/postTodoListItem`,todoListItem); 
    },
    updateItemStatus({commit},itemStatusData) {
        return axios.put(`/api/todolistitem/updateItemStatus/${itemStatusData.id}`,itemStatusData);
    },
    updateTodoListItem({commit},todoListItem) {
        return axios.put(`/api/todolistitem/updateTodoList/${todoListItem.id}`,todoListItem);
    },
    deleteTodoListItem({commit},itemId) {
        return axios.delete(`/api/todolistitem/deleteTodoListItem/${itemId}`); 
    },
    getTodoListItem({commit},itemId) {
        axios.get(`/api/todolistitem/getTodoListItem/${itemId}`).then((response) => {
            commit('setTodoListItem', response.data.payload);
       })
    }
};

const mutations = {
    setTodoListItems: (state, todoListItems) => (state.todoListItems = todoListItems),
    setTodoListItem: (state, todoListItem) => {
        state.todoListItem = todoListItem;
        state.todoListItem.deadLine = new Date(state.todoListItem.deadLine).toISOString().substr(0,10);
        state.todoListItems = state.todoListItems.filter(x=>x.id != todoListItem.id);
    }
};

export default {state, getters, actions, mutations}
