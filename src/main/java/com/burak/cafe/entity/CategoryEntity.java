package com.burak.cafe.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="category2")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   //category2 olabilir
    @Column(name ="category")
    @Size(min = 2, max = 25)
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
