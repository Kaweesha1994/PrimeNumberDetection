package com.dc.PrimeNumberDetection.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.client.RestTemplate;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.NodeDto;

public class Counter implements Runnable {

	public static int counter = 0;
	private static final Object lock = new Object();
	public static ExecutorService executorService;
	private Integer nodeId;

	public static void createThread() {
		executorService = Executors.newFixedThreadPool(20);
	}

	public Counter(Integer nodeId) {
		super();
		this.nodeId = nodeId;
	}

	@Override
	public void run() {
		increaseCounter();

	}

	private void increaseCounter() {
		synchronized (lock) {

			counter++;

			System.out.println(Thread.currentThread().getName() + " : " + counter);

			if (Counter.counter == 1) {

				System.out.println("proxy node id : " + nodeId);

				String url = Constant.SERVER_URL + Bully.getPort() + Constant.GET_NODE_RESPONSE_URL;

				System.out.println("response url : " + url);

				NodeDto nodeDto = new NodeDto();

				nodeDto.setNodeId(nodeId);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.postForObject(url, nodeDto, Object.class);

			}

		}
	}
}
