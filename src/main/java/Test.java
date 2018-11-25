import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Test {


    public static void main(String[] args) {

        System.out.print("hello rushabh kafka 1");

        //create producer property

        Properties properties=new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());




        //create producer

        KafkaProducer<String,String> kafkaProducer=new KafkaProducer<String, String>(properties);

        //create produce record

        ProducerRecord<String,String> producerRecord =new ProducerRecord<String, String>("first_topic","from_ide");




        //send data

        kafkaProducer.send(producerRecord);

        //flush data
        kafkaProducer.flush();

        //flush and close
        kafkaProducer.close();
    }
}
