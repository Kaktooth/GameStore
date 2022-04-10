package com.store.gamestore.model.entity;

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
    private Set<Genre> genres;
    private UUID gameId;

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
