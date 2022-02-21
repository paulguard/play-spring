package geektime.spring.reactor.simple;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

/**
 * @author by 平向东
 * @date 2022/2/17 14:28 Copyright 2021 北京交个朋友数码科技有限公司. All rights reserved.
 */
public class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribed");
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value);
        request(1);
    }

    @Override
    protected void hookOnComplete() {
        System.out.println("Now...Complete！");
    }


}
