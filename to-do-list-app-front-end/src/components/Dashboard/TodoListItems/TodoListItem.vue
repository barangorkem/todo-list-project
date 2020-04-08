<template>
  <div>
    <v-container>
      <v-row>
        <v-col cols="12" md="12">
          <h3>Add Item Page</h3>
          <v-form
            style="padding-top: 30px;"
            ref="form"
            v-model="valid"
            lazy-validation
          >
            <v-col cols="12" md="5">
              <v-text-field
                v-model="todoListItemData.name"
                :rules="nameRules"
                label="Name"
                required
              ></v-text-field>
            </v-col>

            <v-col cols="12" md="5">
              <v-text-field
                v-model="todoListItemData.description"
                :rules="descriptionRules"
                label="Description"
                required
              ></v-text-field>
            </v-col>
            <v-col cols="12" md="5">
              <v-row justify="center">
                <v-date-picker v-model="picker" :min="minDate"></v-date-picker>
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
                  v-model="todoListItemData.relationshipItemId"
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
              @click="addItem()"
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
  name: "TodoListItem",
  computed: mapGetters(["allTodoListItems"]),
  data: function () {
    return {
      valid: true,
      todoListItemData: {
        name: "",
        description: "",
        deadLine: new Date().toISOString(),
        relationshipItemId: 0,
        todoListId: 0,
      },
      picker: new Date().toISOString().substr(0, 10),
      minDate: new Date().toISOString().substr(0, 10),
      nameRules: [(v) => !!v || "Name is required"],
      descriptionRules: [(v) => !!v || "Description is required"],
    };
  },
  methods: {
    ...mapActions(["postTodoListItem", "getTodoListItems"]),
    addItem() {
      if (this.$refs.form.validate()) {
        this.todoListItemData.deadLine = new Date(this.picker).toISOString();
        this.todoListItemData.todoId = parseInt(this.todoId);
        this.postTodoListItem(this.todoListItemData).then(()=>{
            this.$router.push(`/dashboard/todolist/${this.todoId}/items`);
        }).catch(err=>{
            throw err;
        });
      }
    }
  },
  created() {
    this.todoId = this.$route.params.id;
    this.getTodoListItems(this.todoId);
  },
};
</script>

<style></style>
