import Vuex from 'vuex';
import Vue from 'vue';

import user from './modules/user';
import todolist from '../store/modules/todolist';
import todolistitem from './modules/todolistitem';



Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        user,
        todolist,
        todolistitem
    }
});
