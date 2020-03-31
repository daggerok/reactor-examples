package daggerok;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.FluxProcessor;
import reactor.extra.processor.WorkQueueProcessor;

class WorkQueueProcessorTest {

  @Test
  void test_replacement() {
    System.out.println("replacement");

    FluxProcessor<String, String> workQueueProcessor = reactor.extra.processor.WorkQueueProcessor.create();
    //
    workQueueProcessor.subscribe(s -> System.out.println("A: " + s));
    workQueueProcessor.subscribe(s -> System.out.println("B: " + s));
    //
    workQueueProcessor.onNext("a");
    workQueueProcessor.onNext("b");
    workQueueProcessor.onNext("c");
    //
    Try.run(() -> Thread.sleep(1234));
  }

  @Test
  void test_deprecated() {
    System.out.println("deprecated");

    FluxProcessor<String, String> workQueueProcessor = reactor.core.publisher.WorkQueueProcessor.create();
    //
    workQueueProcessor.subscribe(s -> System.out.println("A: " + s));
    workQueueProcessor.subscribe(s -> System.out.println("B: " + s));
    //
    workQueueProcessor.onNext("a");
    workQueueProcessor.onNext("b");
    workQueueProcessor.onNext("c");
    //
    Try.run(() -> Thread.sleep(1234));
  }
}
