/****************************************************************************************
 * Z Group | All Rights Reserved                             *
 ****************************************************************************************/
package com.xcmg.i18n.test;

//import com.alibaba.ververica.cdc.connectors.postgres.PostgreSQLSource;
//import org.apache.flink.streaming.api.datastream.DataStreamSource;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.api.functions.source.SourceFunction;

import com.ververica.cdc.connectors.base.source.jdbc.JdbcIncrementalSource;
import com.ververica.cdc.connectors.postgres.source.PostgresSourceBuilder;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.Properties;

/**
 * @version 1.0<br>
 * @description: <br>
 * @author: <a href="bianchangcai@zgroup.com">Bianchangcai</a>
 * @date: 2025-03-05 22:19
 */
@SpringBootApplication
public class I18NProjDemoApplication {

    private static final long DEFAULT_HEARTBEAT_MS = Duration.ofMinutes(5).toMillis();

    public static void main(String[] args) {
        SpringApplication.run(I18NProjDemoApplication.class, args);
        try {
            flinkCDCListener2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void flinkCDCListener() throws Exception {
//
//        //TODO 创建执行环境
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//        Properties properties = new Properties();
//        properties.setProperty("snapshot.mode", "never");
//        properties.setProperty("debezium.slot.name", "pg_cdc");
//        properties.setProperty("debezium.slot.drop.on.stop", "true");
//        properties.setProperty("include.schema.changes", "true");
//        //使用连接器配置属性启用定期心跳记录生成
//        properties.setProperty("heartbeat.interval.ms", String.valueOf(DEFAULT_HEARTBEAT_MS));
//
//        //TODO 创建Flink-PgSQL-CDC的Source 读取生产环境pgsql数据库
//        SourceFunction<String> pgsqlSource = PostgreSQLSource.<String>builder()
//                .hostname("10.187.137.11")
//                .port(5432)
//                .database("tech_lake_whs_cdc") // monitor postgres database
//                .schemaList("lake_whs_cdc_dev")  // monitor inventory schema
//                .tableList("lake_whs_cdc_dev.tmp_res_f_xxx_hist_incident") // monitor products table
//                .username("cdc_dev")
//                .password("XcmG_Cdc#0320")
//                //反序列化
//                .deserializer(new MyDebezium())
//                //标准逻辑解码输出插件
//                .decodingPluginName("pgoutput")
//                //配置
//                .debeziumProperties(properties)
//                .build();
//
//        //TODO 使用CDC Source从PgSQL读取数据
//        DataStreamSource<String> pgsqlDS = env.addSource(pgsqlSource);
//
//        //TODO 将数据输出到kafka中
//        //pgsqlDS.addSink(MyKafkaUtil.getKafkaSink("***"));
//
//        //TODO 打印到控制台
//        pgsqlDS.print();
//
//        //TODO 执行任务
//        env.execute();
//    }

    /*
     * @Description //TODO 
     * @Date  
     * @Param 
     * @return 
     * @Author 
     **/
    
    /**
     * @Description //TODO
     * @Date ${TIME} ${DATE}
     * @Param 
     * @return 
     * @Author ${USER}
     */
    public static void flinkCDCListener2() throws Exception {
        DebeziumDeserializationSchema<String> deserializer =
                new JsonDebeziumDeserializationSchema();

        JdbcIncrementalSource<String> postgresIncrementalSource =
                PostgresSourceBuilder.PostgresIncrementalSource.<String>builder()
                        .hostname("10.187.137.11")
                        .port(5432)
                        .database("tech_lake_whs_cdc")
                        .schemaList("lake_whs_cdc_dev")
                        .tableList("lake_whs_cdc_dev.tmp_res_f_xxx_hist_incident")
                        .username("cdc_dev")
                        .password("XcmG_Cdc#0320")
                        .slotName("flink")
                        .decodingPluginName("pgoutput") // use pgoutput for PostgreSQL 10+
                        .deserializer(deserializer)
                        .includeSchemaChanges(true) // output the schema changes as well
                        .splitSize(2) // the split size of each snapshot split
                        .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(3000);
        env.fromSource(
                        postgresIncrementalSource,
                        WatermarkStrategy.noWatermarks(),
                        "PostgresParallelSource")
                .setParallelism(2).addSink(new CustomSink());
        //.print();

        env.execute("Output Postgres Snapshot");
    }

}
