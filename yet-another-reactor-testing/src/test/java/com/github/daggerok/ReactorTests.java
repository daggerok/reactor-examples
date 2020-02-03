package com.github.daggerok;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;

@Log4j2
class ReactorTests {

  @Test
  void test_Flux_just_vararg() {
    Flux.just("Hello", "World")
        .subscribe(log::info);
  }

  @Test
  void test_Flux_just_iterable() {
    Flux.just(asList("ololo", "trololo"))
        .subscribe(list -> log.info(list.toString()));
  }

  @Test
  void test_Flux_from_iterable() {
    Flux.fromIterable(asList("ololo", "trololo"))
        .subscribe(log::info);
  }

  @Test
  void test_Flux_zip() {
    Flux.fromIterable(asList("ololo", "trololo"))
        .zipWith(Flux.range(0, 2), (string, integer) -> integer + ". " + string)
        .subscribe(log::info);
  }

  @Test
  void test_Flux_map() {
    Flux.just("ololo trololo",
              "hohoho nonono")
        .map(line -> line.split("\\s+")) // "ololo trololo" -> ["ololo", "trololo"]
        .map(Arrays::asList) // Array("ololo", "trololo") -> List("ololo", "trololo")
        .zipWith(Flux.range(0, 2), (string, integer) -> integer + ". " + string)
        .subscribe(log::info);
  }

  @Test
  void test_Flux_flatMap() {
    Flux.just("ololo trololo",
              "hohoho nonono")
        .map(line -> line.split("\\s+")) // "ololo trololo" -> ["ololo", "trololo"]
        .map(Arrays::asList) // Array("ololo", "trololo") -> List("ololo", "trololo")
        .flatMap(Flux::fromIterable) // [List("ololo", "trololo"), List("hohoho nonono")] -> ["ololo", "trololo",
        // "hohoho nonono"]
        .zipWith(Flux.range(0, Integer.MAX_VALUE), (string, integer) -> integer + ". " + string)
        .subscribe(log::info);
  }

  @Test
  void test_Flux_sort() {
    Flux.just("ololo trololo",
              "hohoho nonono",
              "hohoho ololo")
        .map(line -> line.split("\\s+")) // "ololo trololo" -> ["ololo", "trololo"]
        .map(Arrays::asList) // Array("ololo", "trololo") -> List("ololo", "trololo")
        .flatMap(Flux::fromIterable) // [List("ololo", "trololo"), List("hohoho nonono")] -> ["ololo", "trololo",
        // "hohoho nonono"]
        .sort()
        .zipWith(Flux.range(0, Integer.MAX_VALUE), (string, integer) -> integer + ". " + string)
        .subscribe(log::info);
  }

  @Test
  void test_Flux_distinct() {
    Flux.just("ololo trololo",
              "hohoho nonono",
              "hohoho ololo")
        .map(line -> line.split("\\s+")) // "ololo trololo" -> ["ololo", "trololo"]
        .map(Arrays::asList) // Array("ololo", "trololo") -> List("ololo", "trololo")
        .flatMap(Flux::fromIterable) // [List("ololo", "trololo"), List("hohoho nonono")] -> ["ololo", "trololo",
        // "hohoho nonono"]
        .sort()
        .distinct()
        .zipWith(Flux.range(0, Integer.MAX_VALUE), (string, integer) -> integer + ". " + string)
        .subscribe(log::info);
  }

  @Test
  void test_Flux_interval() throws InterruptedException {
    Flux.zip(Flux.interval(Duration.ofMillis(333)),
             Flux.range(0, 3))
        .map(tick -> "tick" + tick)
        .subscribe(log::info);

    TimeUnit.SECONDS.sleep(1);
  }

  @Test
  void test_Flux_merge() throws InterruptedException {
    Flux<Integer> range = Flux.range(0, Integer.MAX_VALUE);
    Flux.merge(Flux.zip(range, Flux.interval(Duration.ofMillis(300)))
                   .map(tick -> "interval-3 " + tick),
               Flux.zip(range, Flux.interval(Duration.ofMillis(500)))
                   .map(tick -> "interval-2 " + tick))
        .subscribe(log::info);

    TimeUnit.SECONDS.sleep(1);
  }

  @Test
  void test_StepVerifier() {
    Flux<String> numbers = Flux.range(10, 5)
                               .map(String::valueOf)
                               .doOnNext(log::info);

    StepVerifier.create(numbers)
                //.expectSubscription() // optional
                .expectNext("10")
                .expectNext("11", "12")
                .expectNextCount(2)
                .expectComplete()
                .verify();
  }
}
