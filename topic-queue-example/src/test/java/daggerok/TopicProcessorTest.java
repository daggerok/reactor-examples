package daggerok;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.FluxProcessor;

class TopicProcessorTest {

  @Test
  void test_deprecated() {
    System.out.println("deprecated");

    FluxProcessor<String, String> topicProcessor = reactor.core.publisher.TopicProcessor.create();
    //
    topicProcessor.subscribe(s -> System.out.println("A: " + s));
    topicProcessor.subscribe(s -> System.out.println("B: " + s));
    //
    topicProcessor.onNext("a");
    topicProcessor.onNext("b");
    topicProcessor.onNext("c");
    //
    Try.run(() -> Thread.sleep(1234));
  }

  @Test
  void test_replacement() {
    System.out.println("replacement");

    FluxProcessor<String, String> topicProcessor = reactor.extra.processor.TopicProcessor.create();
    //
    topicProcessor.subscribe(s -> System.out.println("A: " + s));
    topicProcessor.subscribe(s -> System.out.println("B: " + s));
    //
    topicProcessor.onNext("a");
    topicProcessor.onNext("b");
    topicProcessor.onNext("c");
    //
    Try.run(() -> Thread.sleep(1234));
  }

  @Test
  void test_alternative() {
    System.out.println("alternative");

    FluxProcessor<String, String> topicProcessor = reactor.core.publisher.DirectProcessor.create();
    //
    topicProcessor.subscribe(s -> System.out.println("A: " + s));
    topicProcessor.subscribe(s -> System.out.println("B: " + s));
    //
    topicProcessor.onNext("a");
    topicProcessor.onNext("b");
    topicProcessor.onNext("c");
    //
    Try.run(() -> Thread.sleep(1234));
  }
}
