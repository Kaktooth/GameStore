package com.store.gamestore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@EqualsAndHashCode(callSuper = true)
public class UserImage extends Image {
    private UUID userId;

    public UserImage(UUID userId, byte[] imageData) {
        super(imageData);
        this.userId = userId;
    }
}
