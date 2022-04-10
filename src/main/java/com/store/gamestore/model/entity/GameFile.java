package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameFile {
    private Integer id;
    private Integer objectId;
    private String name;
    private String version;
    private MultipartFile multipartFile;
    private UUID gameId;
}
