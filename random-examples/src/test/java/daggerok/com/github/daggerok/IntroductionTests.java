package daggerok.com.github.daggerok;

import daggerok.extensions.CaptureSystemOutput;
import daggerok.extensions.CaptureSystemOutput.OutputCapture;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import static org.hamcrest.CoreMatchers.containsString;

@Slf4j
@CaptureSystemOutput
@DisplayName("Reactor 3 introduction: https://www.codingame.com/playgrounds/929/reactive-programming-with-reactor-3/Intro?fbclid=IwAR3aKiqkI1_xUr5zrwqzQz-4djGdtRbbOuBR8FC9wWfxgH33MUBvvm3fuFQ")
class IntroductionTests {

  @Test
  @DisplayName("Should create an expected subscription")
  void should_create_an_expected_subscription(final OutputCapture output) {

    Flux.just("123")
        .map(s -> "A" + s)
        .subscribe(System.out::println);

    output.expect(containsString("A"));
    output.expect(containsString("123"));
    output.expect(containsString("A123"));

  }
}
