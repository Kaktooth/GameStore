package com.store.gamestore.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoreBannerItem extends Image {
    private UUID userId;
    private UUID gameId;
    private String description;

    public StoreBannerItem(UUID user, UUID game,
                           String description, byte[] imageData) {
        super();
        this.setUserId(user);
        this.setGameId(game);
        this.setDescription(description);
        this.setImageData(imageData);
    }

    public StoreBannerItem(StoreBannerItemDTO dto) throws IOException {
        this.setUserId(dto.getUserId());
        this.setGameId(dto.getGameId());
        this.setDescription(dto.getDescription());
        this.setImageData(dto.getImage().getBytes());
    }
}

