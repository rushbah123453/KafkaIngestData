import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class ProducerWithKey  {


    public static void main(String[] args) {

        System.out.print("hello rushabh kafka 1");

        final Logger logger= LoggerFactory.getLogger(Test.class);
        String topic="first_topic";
        String value="body";
        String key="key1";

        //create producer property

        Properties properties=new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());




        //create producer

        KafkaProducer<String,String> kafkaProducer=new KafkaProducer<String, String>(properties);

        //create produce record

        //a key will get same partition every time

        ProducerRecord<String,String> producerRecord =new ProducerRecord<String, String>(topic,key,value);




        //send data

        kafkaProducer.send(producerRecord, new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                //executes when a record is sent succesfully
                if (e==null){

                    logger.info("Data sent with metadata \n"+
                            "Topic: "+ recordMetadata.topic() + "\n" +
                            "Partition: "+recordMetadata.partition() + "\n" +
                            "Offset: " + recordMetadata.offset() + "\n" +
                            "Timestamp: " + recordMetadata.timestamp()
                    );


                }else{

                    logger.error("Error",e);

                }

            }
        });

        //flush data
        kafkaProducer.flush();

        //flush and close
        kafkaProducer.close();
    }
}
