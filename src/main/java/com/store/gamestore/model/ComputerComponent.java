package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComputerComponent {
    private Integer id;
    private String name;
}