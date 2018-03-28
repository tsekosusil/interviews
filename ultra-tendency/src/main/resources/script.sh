
command to start server
sh /usr/lib/kafka/bin/kafka-server-start.sh /etc/kafka/conf/server.properties
 
command to create topic
sh /usr/lib/kafka/bin/kafka-topics.sh --create --topic my-example-topic  --zookeeper localhost:2181 --partitions 1 --replication-factor 1

command to create consumner
sh /usr/lib/kafka/bin/kafka-console-consumer --zookeeper localhost:2181 --topic my-example-topic

command to create producer
sh /usr/lib/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic my-example-topic


    
    
    
 