


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class ConsumerWithThreads {


    public static void main(String[] args) {
      new ConsumerWithThreads().run();

    }

    ConsumerWithThreads(){

    }

    public void run(){
        Logger logger= LoggerFactory.getLogger(Consumer.class.getName());
        CountDownLatch countDownLatch=new CountDownLatch(1);
        Runnable mCosumerThread=new ConsumerThread(countDownLatch,"first_topic");
        Thread mThread=new Thread(mCosumerThread);
        mThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logger.info("caught shutdown hook");
            ((ConsumerThread) mCosumerThread).shutdown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("application has exited");
        }



                ));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("application got interuptted");
        }finally {
            logger.info("application is closing");
        }
    }




    public class ConsumerThread implements Runnable{

      private CountDownLatch latch; // to shutdown application correctly
        KafkaConsumer<String,String> kafkaConsumer ;
        Logger logger= LoggerFactory.getLogger(Consumer.class.getName());

        public ConsumerThread(CountDownLatch countDownLatch,String topic){
          this.latch=countDownLatch;

            //create consumer properties i.e ConsumerConfig
            Properties properties=new Properties();
            properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
            properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"Ide_Consumer");
            properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

            kafkaConsumer=new KafkaConsumer<String, String>(properties);

            //subscribe consumer to 1 or many topics
            kafkaConsumer.subscribe(Arrays.asList("first_topic"));

        }

        @Override
        public void run() {


            try {

                //ask for data
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

                    for (ConsumerRecord<String, String> records1 : records) {

                        logger.info("Consumer data: key" + records1.key() + " value:" + records1.value());


                    }


                }
            }catch (WakeupException e){
                logger.error("Recieved Shutdown signal :",e);

            }finally {
                kafkaConsumer.close();
                //tell main code that we are done with consumer
                latch.countDown();
            }


        }

        public void shutdown(){

            //wakeup() is special method tointerrupt .poll()
            //it will throw wakeup() exception
            kafkaConsumer.wakeup();

        }



    }

}

