package com.dc.PrimeNumberDetection;

import java.util.Date;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import com.dc.PrimeNumberDetection.bully.Bully;

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

			registerNode();

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

}
