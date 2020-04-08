<template>
  <div>
    <v-container>
         <v-row >
             <v-col cols="12" md=12>
      <h3>SignIn Page</h3>
      <v-form style="padding-top: 30px;" ref="form" v-model="valid" lazy-validation>
           <v-text-field
          v-model="userData.username"
          :rules="usernameRules"
          label="Username"
          required
        ></v-text-field>
        <v-text-field
          v-model="userData.password"
          :type="'password'"
          label="Password"
          :rules="passwordRules"
          required
        ></v-text-field>
        <v-btn
          class="mr-4"
          color="primary"
          style="float: right;"
          @click="signIn()"
          >SignIn</v-btn
        >
      </v-form>
    <router-link to="/signUp" style="text-decoration:none;">
If you do not have an account sign up</router-link>

             </v-col>
         </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import axios from 'axios';

export default {
  name: "SignIn",
  data: function () {
    return {
      valid: true,
      userData: {
        email: "",
        password: ""
      },
     usernameRules: [
        (v) => !!v || "Username is required.",
        //   v => v.length > 7 || "Şifre 8 karakter olmalıdır"
      ],
      passwordRules: [
        (v) => !!v || "Password is required.",
        //   v => v.length > 7 || "Şifre 8 karakter olmalıdır"
      ],
    };
  },
  methods: {
    ...mapActions(["signInUser"]),
    signIn() {
      if (this.$refs.form.validate()) {
        this.snackbar = true;
            this.signInUser(this.userData).then(response => { 
            localStorage.setItem("userToken",response.headers.authorization);
            axios.defaults.headers.common['Authorization'] = 'Bearer '+response.headers.authorization;
            this.$router.push('/dashboard');
        })
        .catch(error => {
            alert("Username or password not valid")
        });

      }
    },
  },
};
</script>

<style></style>
