package daggerok.com.github.daggerok;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
@DisplayName("Reactor 3 introduction: https://www.codingame.com/playgrounds/929/reactive-programming-with-reactor-3/Intro?fbclid=IwAR3aKiqkI1_xUr5zrwqzQz-4djGdtRbbOuBR8FC9wWfxgH33MUBvvm3fuFQ")
class IntroductionTests {

  @Test
  @DisplayName("Should create an expected subscription")
  void should_create_an_expected_subscription() {

    Flux<String> flux = Flux.just("123")
                            .map(s -> "A" + s);
    StepVerifier.create(flux)
                .expectNext("A123")
                .verifyComplete();
  }
}
