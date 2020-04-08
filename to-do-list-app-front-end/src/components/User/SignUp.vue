<template>
  <div>
    <v-container>
         <v-row >
             <v-col cols="12" md=12>
      <h3>SignUp Page</h3>
      <v-form style="padding-top: 30px;" ref="form" v-model="valid" lazy-validation>
        <v-text-field
          v-model="userData.username"
          :rules="usernameRules"
          label="Username"
          required
        ></v-text-field>
        <v-text-field
          v-model="userData.email"
          label="E-mail"
          :rules="emailRules"
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
          @click="signUp()"
          >SignUp</v-btn
        >
      </v-form>
             </v-col>
         </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "SignUp",
  data: function () {
    return {
      valid: true,
      userData: {
        email: "",
        password: "",
        username: "",
      },
      emailRules: [
        (v) => !!v || "Email is required.",
        (v) => /.+@.+/.test(v) || "E-mail format not valid",
      ],
      passwordRules: [
        (v) => !!v || "Password is required",
        //   v => v.length > 7 || "Şifre 8 karakter olmalıdır"
      ],
      usernameRules: [
        (v) => !!v || "Username is required",
        //   v => v.length > 7 || "Şifre 8 karakter olmalıdır"
      ],
    };
  },
  methods: {
    ...mapActions(["signUpUser"]),
    signUp() {
      if (this.$refs.form.validate()) {
        this.snackbar = true;
            this.signUpUser(this.userData).then(response => { 
            // alert(response.data.message);
            this.$router.push('/');
        })
        .catch(error => {
            alert(error.response.data.message);
        });

      }
    },
  },
};
</script>

<style></style>
