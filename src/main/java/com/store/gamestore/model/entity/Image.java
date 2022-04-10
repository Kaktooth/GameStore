package com.store.gamestore.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Image {
    private byte[] imageData;

    public String getBase64ImageData() {
        return Base64.encodeBase64String(imageData);
    }
}
