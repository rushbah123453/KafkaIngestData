import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {


    public static void main(String[] args) {
        System.out.print("In Consumer Class");

        Logger logger= LoggerFactory.getLogger(Consumer.class.getName());

        //create consumer properties i.e ConsumerConfig
        Properties properties=new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"Ide_Consumer");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //create consumer
        KafkaConsumer<String,String> kafkaConsumer=new KafkaConsumer<String, String>(properties);

        //subscribe consumer to 1 or many topics
        kafkaConsumer.subscribe(Arrays.asList("first_topic"));


        //ask for data
        while (true){
         ConsumerRecords<String,String> records= kafkaConsumer.poll(Duration.ofMillis(100));

         for (ConsumerRecord<String,String> records1:records){

             logger.info("Consumer data: key"+records1.key()+" value:"+records1.value());


         }


        }


    }

}
