<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12" md="12">
          <h3>Item Detail Page</h3>
          <v-form
            style="padding-top: 30px;"
            ref="form"
            v-model="valid"
            lazy-validation
          >
            <v-col cols="12" md="5">
              <v-text-field
                v-model="todoListItem.name"
                :rules="nameRules"
                label="Name"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="5">
              <v-text-field
                v-model="todoListItem.description"
                :rules="descriptionRules"
                label="Description"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="5">
              <v-row justify="center">
                <v-date-picker v-model="todoListItem.deadLine" :min="minDate"></v-date-picker>
              </v-row>
            </v-col>
            <v-col cols="12" md="5">
              <v-row justify="center">
                <v-select
                  :items="
                    allTodoListItems.concat([
                      { id: 0, name: 'No relationship' },
                    ])
                  "
                  v-model="todoListItem.relationshipItemId"
                  label="Select"
                  single-line
                  item-text="name"
                  item-value="id"
                ></v-select>
              </v-row>
            </v-col>
            <v-btn
              class="mr-4"
              color="primary"
              style="float: right;"
              @click="updateItem()"
              >Add</v-btn
            >
            <!-- <v-col cols="12" md="5">
              <v-text-field
                v-model="todoListData.deadLine"
                :rules="descriptionRules"
                label="Description"
                required
              ></v-text-field>
            </v-col> -->
          </v-form>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "TodoListItemDetail",
  computed: mapGetters(["allTodoListItems","todoListItem"]),
  data: function () {
    return {
      valid: true,
      minDate: new Date().toISOString().substr(0, 10),
      nameRules: [(v) => !!v || "Name is required"],
      descriptionRules: [(v) => !!v || "Description is required"],
      todoId:0
    };
  },
  methods: {
    ...mapActions(["postTodoListItem", "getTodoListItems","getTodoListItem","updateTodoListItem"]),
    updateItem() {
      if (this.$refs.form.validate()) {

        this.todoListItem.todoId = parseInt(this.todoId);
        console.log("this",this.todoListItem);
        this.updateTodoListItem(this.todoListItem).then(()=>{
            this.$router.push(`/dashboard/todolist/${this.todoId}/items`);
        }).catch(err=>{
            throw err;
        });
      }
    }
  },
  created() {
    this.todoId = this.$route.params.id;
    this.itemId = this.$route.params.itemId;
    this.getTodoListItems(this.todoId);
    this.getTodoListItem(this.itemId);
  },
};
</script>

<style></style>
