package com.chihuo.sharding.interfaces.facade;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/producer")
public class MQProducerApi {
	public static final Logger LOGGER = LoggerFactory.getLogger(MQProducerApi.class);

	@Autowired
	private DefaultMQProducer defaultProducer;

	/**
	 * 发送简单的MQ消息
	 * 
	 * @param msg
	 * @return
	 */
	@RequestMapping("/send")
	@ResponseBody
	public String send(@RequestParam(value = "msg", required = false) String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
		if (StringUtils.isEmpty(msg)) {
			return "msg is null";
		}
		LOGGER.info("发送MQ消息内容：" + msg);
		Message sendMsg = new Message("MyTopic", "MyTag", msg.getBytes());
		// 默认3秒超时
		SendResult sendResult = defaultProducer.send(sendMsg);
		LOGGER.info("消息发送响应：" + sendResult.toString());
		return sendResult.toString();
	}
	
	
}
