package com.hamza.kafkaspringcloudstream.handlers;

import com.hamza.kafkaspringcloudstream.events.PageEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class PageEventHandler {

    @Bean
    public Consumer<PageEvent> pageEventConsumer(){
        return (input) -> {
            System.out.println("*******************");
            System.out.println(input.toString());
            System.out.println("*****************");
        };
    }

    @Bean
    public Supplier<PageEvent> pageEventSupplier(){
        return ()->{
            return new PageEvent(
                    Math.random()>0.5?"P1":"P2",
                    Math.random()>0.5?"U1":"U2",
                    new Date(),
                    10+new Random().nextInt(10000)
            );
        };
    }

    @Bean
    public Function<KStream<String, PageEvent>, KStream<String, Long>> kStreamFunction() {
        return (input) ->
                input.filter((k,v)->v.duration()>100)
                        .map((k,v)->new KeyValue<>(v.name(), v.duration()))
                        .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
                        .count()
                        .toStream()
                        .map((k, v) -> new KeyValue<>(k.key(), v));

        // for the filter only the events with duration > 100
        // maping the old key value to new key value where the key is name of the pageEvent and value is the duration of the pageEven
        // for groupBy : it groupBy name and count for each name
        // .toStream to return it to Kstream, because map let it Kstream but groupby make it Ktable, so you need to turn it to kstream
        // .windowed by une propreiter de fenetrage pour reseter la fonction chaque 5 seconds
    }
}
