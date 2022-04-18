package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayInputStream;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameBlob {
    private ByteArrayInputStream bytes;
    private String version;
    private String name;

    public ByteArrayInputStream getBytes() {
        return bytes;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
