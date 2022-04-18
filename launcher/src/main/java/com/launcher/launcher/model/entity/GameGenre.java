package com.launcher.launcher.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameGenre {
    public Set<Genre> genres;
    public UUID gameId;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Genre genre : genres) {
            result.append(genre.toString());
            if (genres.toArray()[genres.size() - 1] != genre) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
