package com.github.daggerok;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
class ReactorTests {

  @Test
  void hello() {
    Flux.just("Hello", "World")
        .subscribe(log::info);
  }
}
