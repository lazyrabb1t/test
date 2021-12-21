package xyz.lazyrabbit.reactor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月21日 10:33:00
 */
public class Test {
    public static void main(String[] args) {
        Flux<String> fruitFlux = Flux
                .just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        fruitFlux.subscribe(e -> System.out.println(e));
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }
}
