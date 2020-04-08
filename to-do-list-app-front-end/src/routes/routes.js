

import SignIn from '../components/User/SignIn.vue';
import SignUp from '../components/User/SignUp.vue';
import Dashboard from '../components/Dashboard/Dashboard.vue';
import TodoLists from '../components/Dashboard/TodoList/TodoLists.vue';
import TodoList from '../components/Dashboard/TodoList/TodoList.vue';
import TodoListItems from '../components/Dashboard/TodoListItems/TodoListItems.vue';
import TodoListItem from '../components/Dashboard/TodoListItems/TodoListItem.vue';
import TodoListItemDetail from '../components/Dashboard/TodoListItems/TodoListItemDetail.vue';

export const routes=[{
    path:'/',
    component:SignIn,
    name:"signin",
    required:false
  },
  {
    path:'/signUp',
    component:SignUp,
    name:"signup",
    required:false
  },
  { path: '/dashboard', component: Dashboard,name:"dashboard",
  children: [
    {
      path: '',
      component: TodoLists,
      meta:{
        required:true
      },
      name:"todolists"
    },
    {
      path: 'todolist',
      component: TodoList,
      meta:{
        required:true
      },
      name:"todolist"
    },
    {
      path: 'todolist/:id/items',
      component: TodoListItems,
      meta:{
        required:true
      },
      name:"todolistitems"
    },
    {
      path: 'todolist/:id/item',
      component: TodoListItem,
      meta:{
        required:true
      },
      name:"todolistitem"
    },
    {
      path: 'todolist/:id/item/:itemId',
      component: TodoListItemDetail,
      meta:{
        required:true
      },
      name:"todolistitemdetail"
    }

  ],
},

  { path: '*',  redirect: '/'  }];

