package zhangchx.mq.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

/**
 * https://blog.csdn.net/evankaka/article/details/52494412
 * kafka 0.9.0.0版本代码
 * @author Snow
 *
 */
public class Kafka {
	public static void main(String[] args) {
//		KafkaProducer.run();
		//FIXME 由于kafka我下载的是1.1.0，所以customer部分是不兼容的，会报错，1.1.0版本代码在此：https://www.cnblogs.com/yy3b2007com/p/8688289.html
		KafkaConsumer.run(); 
	}
}

class KafkaProducer {
	private final Producer<String, String> producer;  
    public final static String TOPIC = "linlin";  
  
    private KafkaProducer() {  
        Properties props = new Properties();  
        // 此处配置的是kafka的端口  
        props.put("metadata.broker.list", "127.0.0.1:9092");  
        props.put("zk.connect", "127.0.0.1:2181");    
  
        // 配置value的序列化类  
        props.put("serializer.class", "kafka.serializer.StringEncoder");  
        // 配置key的序列化类  
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");  
  
        props.put("request.required.acks", "-1");  
  
        producer = new Producer<String, String>(new ProducerConfig(props));  
    }  
  
    void produce() {  
        int messageNo = 1000;  
        final int COUNT = 1500;  
  
        while (messageNo < COUNT) {  
            String key = String.valueOf(messageNo);  
            String data = "hello kafka message " + key;  
            producer.send(new KeyedMessage<String, String>(TOPIC, key, data));  
            System.out.println(data);  
            messageNo++;  
        }  
    }  
  
    public static void run() {  
        new KafkaProducer().produce();  
    }  
}

class KafkaConsumer {
	
	private final ConsumerConnector consumer;  
	  
    private KafkaConsumer() {  
        Properties props = new Properties();  
        // zookeeper 配置  
        props.put("zookeeper.connect", "127.0.0.1:2181");  
  
        // group 代表一个消费组  
        props.put("group.id", "zcxgroup");  
  
        // zk连接超时  
        props.put("zookeeper.session.timeout.ms", "4000");  
        props.put("zookeeper.sync.time.ms", "200");  
        props.put("rebalance.max.retries", "5");  
        props.put("rebalance.backoff.ms", "1500");  
          
      
        props.put("auto.commit.interval.ms", "1000");  
        props.put("auto.offset.reset", "smallest");  
        // 序列化类  
        props.put("serializer.class", "kafka.serializer.StringEncoder");  
  
        ConsumerConfig config = new ConsumerConfig(props);  
  
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);  
    }  
  
    void consume() {  
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();  
        topicCountMap.put(KafkaProducer.TOPIC, new Integer(1));  
  
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());  
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());  
  
        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);  
        KafkaStream<String, String> stream = consumerMap.get(KafkaProducer.TOPIC).get(0);  
        ConsumerIterator<String, String> it = stream.iterator();  
        while (it.hasNext())  
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + it.next().message() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");  
    }  
  
    public static void run() {  
        new KafkaConsumer().consume();  
    }  
}
