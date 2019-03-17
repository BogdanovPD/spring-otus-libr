package ru.otus.spring.libr.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class NamedEntity extends BaseEntity {

    public NamedEntity(long id, String name) {
        super(id);
        this.name = name;
    }

    protected String name;

    @Override
    public String toString() {
        return name;
    }
}
