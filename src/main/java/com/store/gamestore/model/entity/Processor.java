package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Processor {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return name;
    }
}

