package daggerok;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.WorkQueueProcessor;

class WorkQueueProcessorTest {

  @Test
  void test() {
    WorkQueueProcessor<String> workQueueProcessor = WorkQueueProcessor.create();
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
