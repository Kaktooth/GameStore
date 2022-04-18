package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Image {
    public byte[] imageData;

    public byte[] getImageData() {
        return imageData;
    }

    public String getBase64ImageData() {
        return Base64.getEncoder().encodeToString(imageData);
    }
}
