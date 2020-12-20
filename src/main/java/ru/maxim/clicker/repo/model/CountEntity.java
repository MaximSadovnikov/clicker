package ru.maxim.clicker.repo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "counter")
public class CountEntity {
    @Id private Integer count;

    public CountEntity(Integer count) {
        this.count = count;
    }

    public CountEntity() { }

    public Integer getCount() {
        return count;
    }
}
