package com.boookk.bithumbpractice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
class BithumbPracticeApplicationTests {

    /**
     * 1. ["Blenders", "Old", "Johnnie"] 와 "[Pride", "Monk", "Walker”] 를 순서대로 하나의 스트림으로 처리되는 로직 검증
     */
    @Test
    public void concatWithDelay() {
        Flux<String> names1$ = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names2$ = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> names$ = Flux.concat(names1$, names2$)
                .log();

        StepVerifier.create(names$)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }


    /**
     * 2. 1~100 까지의 자연수 중 짝수만 출력하는 로직 검증
     * 개수와 출력되는 값을 확인
     */
    @Test
    public void printEvent() {
        Flux<Integer> numbers = Flux.range(1, 100)
                .filter(i -> i % 2 == 0)
                .doOnNext(i -> System.out.println(i));


        StepVerifier.create(numbers)
                .expectNextCount(50)
                .thenConsumeWhile(i -> {
                    Assertions.assertEquals(0, i % 2);
                    return true;
                })
                .verifyComplete();
    }


    /**
     * 3. “hello”, “there” 를 순차적으로 publish하여 순서대로 나오는지 검증
     */
    @Test
    public void publishFlow() {
        Flux<String> c = Flux.just("hello", "there")
                .publishOn(Schedulers.single())
                .log();

        StepVerifier.create(c)
                .expectNext("hello", "there")
                .verifyComplete();
    }


    /**
     * 4. 아래와 같은 객체가 전달될 때 “JOHN”, “JACK” 등 이름이 대문자로 변환되어 출력되는 로직 검증
     * Person("John", "[john@gmail.com](mailto:john@gmail.com)", "12345678")
     * Person("Jack", "[jack@gmail.com](mailto:jack@gmail.com)", "12345678")
     */
    @Test
    public void upperName() {
        Person p1 = new Person("John", "[john@gmail.com](mailto:john@gmail.com)", "12345678");
        Person p2 = new Person("Jack", "[jack@gmail.com](mailto:jack@gmail.com)", "12345678");

        Flux<Person> person = Flux.just(p1, p2)
                .doOnNext(p -> System.out.println(p.getName().toUpperCase()))
                .log();

        StepVerifier.create(person)
                .assertNext(p -> p.getName().equals("JOHN"))
                .assertNext(p -> p.getName().equals("JACK"))
                .verifyComplete();
    }


    /**
     * 5. ["Blenders", "Old", "Johnnie"] 와 "[Pride", "Monk", "Walker”]를 압축하여 스트림으로 처리 검증
     */
    @Test
    public void zipList() {
        Flux<String> lst1 = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> lst2 = Flux.just("Pride", "Monk", "Walker");
        Flux<String> lst = Flux.zip(lst1, lst2, (a, b) -> a + " " + b)
                .log();

        StepVerifier.create(lst)
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker")
                .verifyComplete();
    }


    /**
     * 6. ["google", "abc", "fb", "stackoverflow”] 의 문자열 중 5자 이상 되는 문자열만 대문자로 비동기로 치환하여 1번 반복하는 스트림으로 처리하는 로직 검증
     */
    @Test
    public void searchString() {
        Flux<String> lst = Flux.just("google", "abc", "fb", "stackoverflow")
                .filter(i -> i.length() >= 5)
                .flatMap(i -> Mono.just(i.toUpperCase()))
                .repeat(1)
                .log();

        StepVerifier.create(lst)
                .expectNext("GOOGLE", "STACKOVERFLOW", "GOOGLE", "STACKOVERFLOW")
                .verifyComplete();
    }
}
