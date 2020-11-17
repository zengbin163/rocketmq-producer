package com.chihuo.sharding.infrastructure.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "rocketmq.producer")
public class MQProducerConfiguration {

	public static final Logger LOGGER = LoggerFactory.getLogger(MQProducerConfiguration.class);

	private String groupName;
	private String namesrvAddr;
	// 消息最大值
	private Integer maxMessageSize;
	// 消息发送超时时间
	private Integer sendMsgTimeOut;
	// 失败重试次数
	private Integer retryTimesWhenSendFailed;

	/**
	 * mq 生成者配置
	 * 
	 * @return
	 * @throws MQClientException
	 */
	@Bean(name = "defaultProducer")
	//@ConditionalOnProperty(prefix = "rocketmq.producer", value = "isOnOff", havingValue = "on")
	public DefaultMQProducer defaultProducer() throws MQClientException {
		LOGGER.info("defaultProducer 正在创建---------------------------------------");
		DefaultMQProducer producer = new DefaultMQProducer(groupName);
		producer.setNamesrvAddr(namesrvAddr);
		producer.setVipChannelEnabled(false);
		producer.setMaxMessageSize(maxMessageSize);
		producer.setSendMsgTimeout(sendMsgTimeOut);
		producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendFailed);
		producer.start();
		LOGGER.info("rocketmq producer server 开启成功----------------------------------");
		return producer;
	}

}
