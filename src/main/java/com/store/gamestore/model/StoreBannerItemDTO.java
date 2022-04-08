package com.store.gamestore.model;

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
public class StoreBannerItemDTO {
    private UUID gameId;
    private UUID userId;
    private MultipartFile image;
    private String description;
}
