<template>
  <v-container>
    <h3>Your TodoLists Page</h3>
    <v-simple-table fixed-header>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">#</th>
            <th class="text-left">Name</th>
            <th class="text-left"></th>
            <th class="text-right">
              <v-btn
                class="mr-4"
                color="primary"
                style="float: right;"
                @click="addTodoList()"
                >AddTodoList</v-btn
              >
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in allTodoLists" :key="index + 1">
            <td @click="routeTodoListDetail(item.id)" style="cursor:pointer;">{{ index + 1 }}</td>
            <td @click="routeTodoListDetail(item.id)"  style="cursor:pointer;">{{ item.name }}</td>
            <td>
              <v-btn class="mr-4" color="error" @click="removeTodoList(item.id)"
                >Delete</v-btn
              >
            </td>
            <td></td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "TodoLists",
  computed: mapGetters(["allTodoLists"]),
  data: function () {
    return {};
  },
  methods: {
    ...mapActions(["getTodoLists","deleteTodoList","setTodoLists"]),
    addTodoList() {
      this.$router.push("/dashboard/todolist");
    },
    removeTodoList(todoListId) {
      this.deleteTodoList(todoListId)
        .then((response) => {
          alert(response.data.message);
          this.$router.go(0)
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
    routeTodoListDetail(todoListId){
          this.$router.push(`/dashboard/todolist/${todoListId}/items`);
    }
  },
  created() {
    this.getTodoLists();
  },
};
</script>

<style></style>
