package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    public Integer id;
    public String name;

    @Override
    public String toString() {
        return name;
    }
}
