package com.dc.PrimeNumberDetection;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class PrimeNumberDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimeNumberDetectionApplication.class, args);

		Date currentTime = new Date();

		long timeMili = currentTime.getTime();

		System.out.println("time in mili : " + timeMili);

		long randomLong = new Random().nextLong();

		System.out.println("random number : " + randomLong);

		long nodeId = timeMili + randomLong;

		System.out.println("node id before : " + nodeId);

		System.out.println("First arguement : " + args[0]);
		System.out.println("Second arguement : " + args[1]);

		Bully bully = new Bully(args[0], (int) (nodeId < 0 ? nodeId : nodeId * -1), Integer.parseInt(args[1]));

		try {

//			registerNode();

			getPortsOfNodes();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

	private static void registerNode() {

		String url = "http://localhost:8500/v1/agent/service/register";

		RestTemplate restTemplate = new RestTemplate();

		String data = "{\n" + "        \"Name\": \"" + Bully.getNodeName() + "\",\n" + "        \"ID\": \""
				+ Bully.getNodeId() + "\",\n" + "        \"port\": " + Bully.getPort() + ",\n"
				+ "        \"check\": {\n" + "            \"name\": \"Check Counter health " + Bully.getPort() + "\",\n"
				+ "            \"tcp\": \"localhost:" + Bully.getPort() + "\",\n"
				+ "            \"interval\": \"10s\",\n" + "            \"timeout\": \"1s\"\n" + "        }\n"
				+ "    }";

		System.out.println(data);

		restTemplate.put(url, data);

	}

	private static Map<String, Object> getPortsOfNodes() {

		Map<String, Object> map = new HashMap<String, Object>();

		String url = "http://127.0.0.1:8500/v1/agent/services";

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> object = restTemplate.getForEntity(url, Object.class);

		Map<String, Object> o = (LinkedHashMap<String, Object>) object.getBody();

		ObjectMapper mapper = new ObjectMapper();

		for (String key : o.keySet()) {
			System.out.println(key);

			Map<String, Object> map2 = mapper.convertValue(o.get(key), Map.class);

			System.out.println(map2.get("Service"));
			System.out.println(map2.get("Port"));

			map.put(map2.get("Service").toString(), map2.get("Port"));

		}

		return map;

	}

}
