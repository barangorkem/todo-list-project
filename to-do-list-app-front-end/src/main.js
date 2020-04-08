import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import vuetify from './plugins/vuetify';
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import store from './store';
import axios from 'axios';
import VueJwtDecode from 'vue-jwt-decode';


//url
import {BASE_URL} from './util/path';

//routes
import {routes} from './routes/routes';

axios.defaults.baseURL = BASE_URL;
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
axios.defaults.headers.common["Content-Type"] = 'application/json';
axios.defaults.headers.common["Access-Control-Allow-Methods"]="POST, GET, PUT, DELETE";

if(localStorage.getItem("userToken")!=null) {
  axios.defaults.headers.common['Authorization'] = 'Bearer '+localStorage.getItem("userToken");
}



Vue.config.productionTip = false

Vue.use(VueRouter);
Vue.use(VueJwtDecode)


const router = new VueRouter({
  mode: 'history',
  routes:routes
});
// GOOD
router.beforeEach((to, from, next) => {

  if(to.meta.required) {
    if(localStorage.getItem("userToken")!=null) {
      next();
    }
    else {
    next('/');
    }
  }
  else {
    if(localStorage.getItem("userToken")!=null) {
        next('/dashboard');
    }
    next();
  }
next();

})


new Vue({
  vuetify,
  store,
  router,
  render: h => h(App)
}).$mount('#app')
