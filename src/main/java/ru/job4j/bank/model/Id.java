package ru.job4j.bank.model;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public abstract class Id {
    @NotNull(message = "Id must be non null", groups = {
            Operations.OnUpdate.class, Operations.OnDelete.class
    })
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id id1 = (Id) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
