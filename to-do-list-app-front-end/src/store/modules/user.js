import axios from 'axios';
// import {BASE_URL} from '../../util/path';

const state ={
    users: [],
    user:{}
};

const getters = {
    // allUsers:(state) => state.users,
    // userObject:(state) => state.user
};

const actions ={

     signInUser({commit},userObject) {
        return axios.post(`/api/user/signIn`,userObject); 
    },
     signUpUser({commit},userObject) {
         return axios.post(`/api/user/signUp`,userObject);  
    }
};

const mutations ={
    // setUsers:(state,users) => (state.users = users),
    // setUser:(state,user) => (state.user = user)
};

export default {
    state,
    getters,
    actions,
    mutations
}
