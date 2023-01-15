package com.launcher.launcher.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"multipartFile"})
public class GameFile {
    public Integer id;
    public Integer objectId;
    public String name;
    public String version;
    public UUID gameId;
}
