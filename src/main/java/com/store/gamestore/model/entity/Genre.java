package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre implements Serializable {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
