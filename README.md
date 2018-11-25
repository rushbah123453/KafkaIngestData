# Some Important Concept about Kafka

Kafka

Order of msg is only guaranteed within the partition not  across

Data is kept for only limited time , default is1 week

Once data is written to partition, it can’t be changed (immutable) 

Kafka Cluster comprises of multiple brokers(servers)

If producers send key with the msg, all msg for that key will be guaranteed to. 1 partition in order

If you have more consumers than partitions, some consumers have to be inactive. E.g If we have 3 partitions , max we can have 3 consumers

We have 3 delivery semantics for consumers

atMostOnce -> offsets are committed as soon as msg is recieved

Atleastonce -> offsets are committed after msg is processed

Exactly one -> for Kafka ->Kafka workflow, api streams
Kafka->external sys , idempotent consumers

Every Kafka broker is called bootstrap server

You only need to connect to 1 broker to get connected to whole Kafka cluster

Zookeeper manages brokers (keep a list of them)
Zookeeper helps in performing leader election 
Zookeeper sends notification to kafka in case of changes (broker died, comes up etc ..)
Zookeeper by design operates on odd number
Zookeeper does not hold consumer offset 
Zookeeper have leader (handles write) and rest of servers are followers (handles read) 






# Kafka-Ingest-Data

This are some basic commands to get the consumer and producer up and running on command line after installing kafka locally

Start zookeeper:   
 zookeeper-server-start config/zookeeper.properties 

Start server : 
 kafka-server-start config/server.properties

Create a topic (first_topic): 
kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1

List a topic :
kafka-topics --zookeeper 127.0.0.1:2181 --list

Describe a topic(first_topic) :
kafka-topics --zookeeper 127.0.0.1:2181 --topic first_topic --describe

Create a producer:
kafka-console-producer --broker-list 1270.1:9092 --topic first_topic

kafka-console-producer --broker-list 1270.1:9092 --topic first_topic --producer-property acks=all

Consume topic :
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic 

kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning

Consumer topic using a group (group name rushabh):
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --group rushabh

List all consumer group:
rushabhoswal$ kafka-consumer-groups --bootstrap-server localhost:9092 --list


Describe consumer group:
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group rushabh

# Add this Dependencies 

Kafka Dependencies


<dependencies>


    <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
    <dependency>
        <groupId>org.apache.kafka</groupId>
        <artifactId>kafka-clients</artifactId>
        <version>2.0.0</version>
    </dependency>


    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.25</version>
    </dependency>


</dependencies>



#Starting Producer 

First start the zookeeper

Start zookeeper:   
 zookeeper-server-start config/zookeeper.properties


Then start the server 

Start server : 
 kafka-server-start config/server.properties


