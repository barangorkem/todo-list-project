<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12" md="12">
          <h3>Add TodoList Page</h3>
          <v-form
            style="padding-top: 30px;"
            ref="form"
            v-model="valid"
            lazy-validation
          >
            <v-col cols="12" md="5">
              <v-text-field
                v-model="todoListData.name"
                :rules="nameRules"
                label="Name"
                required
              ></v-text-field>
              <v-btn
                class="mr-4"
                color="primary"
                style="float: right;"
                @click="addTodoList()"
                >Add</v-btn
              >
            </v-col>
          </v-form>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "TodoList",
  data: function () {
    return {
      valid: true,
      todoListData: {
        name: "",
      },
      nameRules: [(v) => !!v || "Todolist name is required"],
    };
  },
  methods: {
    ...mapActions(["postTodoList"]),
    addTodoList() {
      if (this.$refs.form.validate()) {
        this.snackbar = true;
        this.postTodoList(this.todoListData).then(response => { 
            this.$router.push('/dashboard');
        })
        .catch(error => {
            alert(error.response.data.message);
        });
      }
    }
  },
};
</script>

<style></style>
