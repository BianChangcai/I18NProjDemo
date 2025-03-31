package com.xcmg.i18n.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

@Slf4j
public class CustomSink extends RichSinkFunction<String> {

    @Override
    public void invoke(String value, Context context) throws Exception {
        log.info("============数据发生变化:{}", value);
    }

}

