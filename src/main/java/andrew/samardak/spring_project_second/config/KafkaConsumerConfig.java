package andrew.samardak.spring_project_second.config;

import andrew.samardak.spring_project_second.dto.request.TransactionAcceptRequestDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionAcceptRequestDto> kafkaTransactionAcceptListenerContainerFactory() {
        return kafkaListenerContainerFactory(consumerFactory(TransactionAcceptRequestDto.class));
    }

    private <V> ConsumerFactory<String, V> consumerFactory(Class<V> valueType) {
        return new DefaultKafkaConsumerFactory<>(Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                JsonDeserializer.VALUE_DEFAULT_TYPE, valueType,
                JsonDeserializer.TRUSTED_PACKAGES, "*",
                JsonDeserializer.TYPE_MAPPINGS, ""
        ));
    }

    private <V> ConcurrentKafkaListenerContainerFactory<String, V> kafkaListenerContainerFactory(ConsumerFactory<String, V> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000, 3)));
        return factory;
    }
}
