package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameImages {
    private MultipartFile storeImage;
    private MultipartFile gamePageImage;
    private MultipartFile collectionImage;
    private MultipartFile[] gameplayImages;
}
