package com.store.gamestore.service.game.search;

import com.store.gamestore.model.entity.UploadedGame;
import com.store.gamestore.repository.search.SearchRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@Qualifier("searchService")
@RequiredArgsConstructor
public class SearchService implements GameSearcher<UploadedGame> {

  private final SearchRepository searchRepository;

  @Override
  public List<UploadedGame> searchGames(String searchString, Integer count) {
    log.info(String.format("Search string: %s", searchString));
    return searchRepository.search(searchString)
        .stream()
        .limit(count)
        .collect(Collectors.toList());
  }
}
