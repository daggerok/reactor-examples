package daggerok;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.TopicProcessor;

class TopicProcessorTest {

  @Test
  void test() {
    TopicProcessor<String> topicProcessor = TopicProcessor.create();
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
