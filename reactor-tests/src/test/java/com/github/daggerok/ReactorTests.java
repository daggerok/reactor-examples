package com.github.daggerok;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static java.util.Arrays.asList;

@Slf4j
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
        .flatMap(Flux::fromIterable) // [List("ololo", "trololo"), List("hohoho nonono")] -> ["ololo", "trololo", "hohoho nonono"]
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
        .flatMap(Flux::fromIterable) // [List("ololo", "trololo"), List("hohoho nonono")] -> ["ololo", "trololo", "hohoho nonono"]
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
        .flatMap(Flux::fromIterable) // [List("ololo", "trololo"), List("hohoho nonono")] -> ["ololo", "trololo", "hohoho nonono"]
        .sort()
        .distinct()
        .zipWith(Flux.range(0, Integer.MAX_VALUE), (string, integer) -> integer + ". " + string)
        .subscribe(log::info);
  }
}
