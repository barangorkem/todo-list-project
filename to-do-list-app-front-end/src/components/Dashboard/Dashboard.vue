<template>
  <div >
    <v-container>
    <v-row>
    <v-col  md="8"></v-col>
<v-col md="4">
<v-navigation-drawer permanent style="width:100%!important;" >
     <v-list>
      <v-list-group
      style="background-color:gainsboro;"
        prepend-icon="account_circle"
      >
        <template v-slot:activator>
          <v-list-item-title>{{username}}</v-list-item-title>
        </template>
        <v-list-group
        >
          <template v-slot:activator>
            <v-list-item-content @click="logOut()">
              <v-list-item-title>Çıkış Yap</v-list-item-title>
            </v-list-item-content>
          </template>
        </v-list-group>
      </v-list-group>
    </v-list>
    </v-navigation-drawer>
    </v-col>
    </v-row>
      </v-container>
      <v-row>
    <v-col cols="12" md="12">
      <router-view></router-view>
    </v-col>
    </v-row>
  </div>
</template>

<script>
import VueJwtDecode from 'vue-jwt-decode'

export default {
    name:"Dashboard",
    methods: {
    logOut() {
    localStorage.removeItem("userToken");
    this.$router.push("/");
    }
  },
    data: function () {
    return {
      username: "",
    };
  },
  created(){
    if(localStorage.getItem("userToken")!=null) {
      let userData = VueJwtDecode.decode(localStorage.getItem("userToken"));
      this.username = userData.sub; 

    }
  }
}
</script>

<style>

</style>