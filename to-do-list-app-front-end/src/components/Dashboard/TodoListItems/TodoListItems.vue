<template>
  <v-container>
    <h3>TodoListItems Page</h3>
       <v-btn
                class="mr-4"
                color="primary"
                style="float: right;margin-top:-45px;"
                @click="addTodoListItem()"
                >AddItem</v-btn
              >
       <v-row align="center">
    <v-col cols="12">
      <v-select
        :items="items"
        item-text="name"
        item-value="value"
        v-model="filterId"
        @change="todoListItemFilter()"
        :menu-props="{ top: true, offsetY: true }"
        label="Filter"
      ></v-select>
    </v-col>
  </v-row>
  <v-card>
   <v-card-title>
      Nutrition
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search name"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="allTodoListItems"
      :search="search"
      class="elevation-1"
    >
    
      <template v-slot:item.status="{ item }">
        <v-simple-checkbox v-model="item.status" @input="changeItemStatus(item)"></v-simple-checkbox>
      </template>
        <template v-slot:item.actions="{ item }">
      <v-icon
        small
        class="mr-2"
        @click="routeTodoListItemDetail(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click="removeTodoListItem(item)"
      >
        mdi-delete
      </v-icon>
    </template>
    </v-data-table>
  </v-card>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "TodoListItems",
  computed: mapGetters(["allTodoListItems"]),
  data: function () {
    return {
      todoId: 0,
      items: [{name:"No Filter",value:0},{name:"Complete",value:1}, {name:"Not Complete",value:2}, {name:"Expired",value:3}],
      dialog: false,
      search: '',
      filterId:0,
      headers: [
        {
          text: "Name",
          align: "start",
          value: "name",
        },
        { text: "CreateDate", value: "createdAt" },
        { text: "Deadline", value: "deadLine"},
        { text: "Status", value: "status" },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
    };
  },
  methods: {
    ...mapActions([
      "getTodoListItems",
      "updateItemStatus",
      "deleteTodoListItem",
      "getTodoListItemsByFilter"
    ]),
    addTodoListItem() {
      this.$router.push(`/dashboard/todolist/${this.todoId}/item`);
    },
    changeItemStatus(item) {
      console.log("item",item);
      let itemStatusData = {
        id: item.id,
        status: item.status,
        relationshipItemId: item.relationshipId,
      };
      this.updateItemStatus(itemStatusData)
        .then((response) => {
          alert(response.data.message);
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
    removeTodoListItem(item) {
      this.deleteTodoListItem(item.id)
        .then((response) => {
          alert(response.data.message);
          this.$router.go(0);
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
    routeTodoListItemDetail(item) {
      this.$router.push(`/dashboard/todolist/${this.todoId}/item/${item.id}`);
    },
    todoListItemFilter(){
      let todoListFilterData = {
        todoListId:this.todoId,
        filterId:this.filterId
      };
      this.getTodoListItemsByFilter(todoListFilterData);
    }
  },
  created() {
    this.todoId = this.$route.params.id;
    this.getTodoListItems(this.todoId);
    // this.getTodoLists();
  },
};
</script>

<style></style>
