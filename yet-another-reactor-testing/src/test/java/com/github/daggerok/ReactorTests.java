package com.github.daggerok;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class ReactorTests {

    @Test
    void reactor_stages() {
        Flux.just(                      // <- 1. assembling
                   "ololo", "trololo"   // <- 3. Runtime (request, onNext, onError, onComplete)
        ).subscribe();                  // <- 2. subscription
    }
}
