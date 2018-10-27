package daggerok;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class App {
  public static void main(String[] args) {
    Flux.range(0, 10)
        .map(integer -> integer * integer)
        .filter(integer -> integer % 3 != 0)
        .buffer(3)
        .subscribe(integers -> log.info("result: {}", integers));
  }
}
