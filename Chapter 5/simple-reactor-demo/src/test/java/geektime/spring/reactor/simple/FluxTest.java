package geektime.spring.reactor.simple;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.retry.Retry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

/**
 * @author by 平向东
 * @date 2022/2/17 14:05 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public class FluxTest {

    @Test
    public void simpleTest(){

        Flux<Integer> range = Flux.range(3, 2);
        range.subscribe(i -> System.out.println(i));

    }

    @Test
    public void errorTest(){

        Flux<Integer> range = Flux.range(1, 4)
            .map(i -> {
                if (i <= 3) {
                    return i;
                }
                throw new RuntimeException("Got 4");
            });
        range.subscribe(System.out::println, error -> System.out.println("ERROR:"+error));

    }

    @Test
    public void completeTest(){

        Flux<Integer> range = Flux.range(1, 4);
        range.subscribe(System.out::println,
            error -> System.out.println("ERROR:"+error),
            ()-> System.out.println("Mission complete"));

    }

    @Test
    public void limitTest(){

        Flux<Integer> range = Flux.range(1, 4);
        range.subscribe(System.out::println,
            error -> System.out.println("ERROR:"+error),
            ()-> System.out.println("Mission complete"),
            sub -> sub.request(1));

    }

    @Test
    public void sampleSubScribeTest(){

        SampleSubscriber<List<Integer>> ss = new SampleSubscriber<>();
        Flux<Integer> range = Flux.range(1, 10);
        Flux<Integer> integerFlux = range.limitRate(5,1);
        Flux<List<Integer>> buffer = integerFlux.buffer(2);
        Flux<List<Integer>> listFlux = buffer.limitRequest(10);
        listFlux.subscribe(ss);
    }

    @Test
    public void fluxGenerationTest(){

        Flux<String> generate = Flux.generate(() -> 1, (state, sink) -> {
            sink.next("3 x " + state + " = " + 3 * state);
            if (state == 11) {
                sink.complete();
            }

            return state + 2;
        });

        SampleSubscriber<String> ss = new SampleSubscriber<>();
        generate.subscribe(ss);

    }

    @Test
    public void fluxGenerationMutationTest(){

        Flux<String> generate = Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndAdd(2);
            sink.next("3 x " + i + " = " + 3 * i);
            if (i >= 11) {
                sink.complete();
            }

            return state;
        });

        SampleSubscriber<String> ss = new SampleSubscriber<>();
        generate.subscribe(ss);

    }

    @Test
    public void fluxGenerationMutationConsumerTest(){

        Flux<String> generate = Flux.generate(AtomicLong::new, (state, sink) -> {
            long i = state.getAndAdd(1);
            sink.next("3 x " + i + " = " + 3 * i);
            if (i >= 11) {
                sink.complete();
            }

            return state;
        }, (state) -> System.out.println("lastest state: " + state));

        SampleSubscriber<String> ss = new SampleSubscriber<>();
        generate.subscribe(ss);

    }

    @Test
    public void alphabetTest(){

        Flux<String> alphabet = Flux.just(-1, 1, 30, 13, 9, 20)
            .handle((i, sink) -> {
                String letter = alphabet(i);
                if (letter != null)
                    sink.next(letter);
            });

        alphabet.subscribe(System.out::println);

    }

    @Test
    public void threadingAndSchedulers() throws InterruptedException {

        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
            .map(msg -> msg + "thread ")
            .subscribe(v ->
                System.out.println(v + Thread.currentThread().getName())
            )
        );
        t.start();
        t.join();
    }

    @Test
    public void schedulerTest(){

        Flux<Long> test = Flux.interval(Duration.ofMillis(300), Schedulers.newSingle("test"));

        SampleSubscriber<Long> ss = new SampleSubscriber<>();
        test.subscribe(ss);

    }

    @Test
    public void publishOnTest() throws InterruptedException {

        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
            .range(1, 4)
            .map(i -> {
                System.out.println("Thread:" + Thread.currentThread().getName() + ", i:"+i); return 10 + i;})
            .publishOn(s)
            .map(i -> "value " + i);

        Thread thread = new Thread(() -> flux.subscribe(v-> System.out.println(v + "| " + Thread.currentThread().getName())));
        thread.start();

        Thread.sleep(10000);
    }

    @Test
    public void subscribeOnTest() throws InterruptedException {

        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
            .range(1, 4)
            .map(i -> {
                System.out.println("Thread:" + Thread.currentThread().getName() + ", i:"+i); return 10 + i;})
            .subscribeOn(s)
            .map(i -> "value " + i);

        Thread thread = new Thread(() -> flux.subscribe(v-> System.out.println(v + "| " + Thread.currentThread().getName())));
        thread.start();

        Thread.sleep(10000);
    }

    @Test
    public void disposableTest(){

        AtomicBoolean isDisposed = new AtomicBoolean();
        Disposable disposableInstance = new Disposable() {
            @Override
            public void dispose() {
                isDisposed.set(true);
            }

            @Override
            public String toString() {
                return "DISPOSABLE";
            }
        };

        Flux<String> flux =
            Flux.using(
                () -> disposableInstance,
                disposable -> Flux.just(disposable.toString()),
                Disposable::dispose
            );

        SampleSubscriber<String> stringSampleSubscriber = new SampleSubscriber<>();
        flux.subscribe(stringSampleSubscriber);

        assert isDisposed.get();

    }

    @Test
    public void onErrorReturnTest() throws InterruptedException {

        Flux<String> flux =
            Flux.interval(Duration.ofMillis(1000))
                .map(input -> {
                    if (input < 3) return "tick " + input;
                    throw new RuntimeException("boom");
                })
                .onErrorReturn("Uh oh");

        flux.subscribe(System.out::println);
        Thread.sleep(10000);

    }

    @Test
    public void retryTest() throws InterruptedException {

        Flux.interval(Duration.ofMillis(250))
            .map(input -> {
                if (input < 3) return "tick " + input;
                throw new RuntimeException("boom");
            })
            .retry(1)
            .elapsed()
            .subscribe(System.out::println, System.err::println);

        Thread.sleep(2100);

    }

    @Test
    public void retryWhenTest(){

        Flux<String> flux = Flux
            .<String>error(new IllegalArgumentException())
            .doOnError(System.out::println)
            .retryWhen(Retry.from(companion ->
                companion.take(6)));

        SampleSubscriber<String> ss = new SampleSubscriber<>();
        flux.subscribe(ss);

    }

    @Test
    public void testRetry2(){

        AtomicInteger errorCount = new AtomicInteger();
        AtomicInteger transientHelper = new AtomicInteger();
        Flux<Integer> transientFlux = Flux.<Integer>generate(sink -> {
                int i = transientHelper.getAndIncrement();
                if (i == 12) {
                    sink.next(i);
                    sink.complete();
                }
                else if (i % 3 == 0) {
                    sink.next(i);
                }
                else {
                    sink.error(new IllegalStateException("Transient error at " + i));
                }
            })
            .doOnError(e -> errorCount.incrementAndGet());

        transientFlux.retryWhen(Retry.max(2).transientErrors(true))
            .blockLast();
        assertThat(errorCount).hasValue(8);

    }


    String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

}
