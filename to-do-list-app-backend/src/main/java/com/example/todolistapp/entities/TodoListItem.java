package com.example.todolistapp.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "TodoListItem")
@Data
public class TodoListItem extends BaseEntity {

    @Column(name = "Name")
    @NotBlank(message = "İsim alanı zorunludur.")
    private String name;

    @Column(name = "Description")
    @NotBlank(message = "Açıklama alanı zorunludur.")
    private String description;

    @Column(name = "DeadLine")
    private Date deadLine;

    @Column(name = "Status")
    private Boolean status;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "RelationshipItemId")
    private Long relationshipItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TodoListId", nullable = false)
    private TodoList todoList;



}



