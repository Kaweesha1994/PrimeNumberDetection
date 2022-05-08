package com.dc.PrimeNumberDetection;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.util.NodeUtil;

@SpringBootApplication
public class PrimeNumberDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimeNumberDetectionApplication.class, args);

		Date currentTime = new Date();

		long timeMili = currentTime.getTime();

		System.out.println("time in mili : " + timeMili);

		long randomLong = new Random().nextLong();

		System.out.println("random number : " + randomLong);

		Integer nodeId = (int) (timeMili + randomLong);

		System.out.println("node id before : " + nodeId);

		System.out.println("First arguement : " + args[0]);
		System.out.println("Second arguement : " + args[1]);

		if (nodeId < 0) {
			nodeId *= -1;

			System.out.println("node id : " + nodeId);
		}

		Bully bully = new Bully(args[0], nodeId, Integer.parseInt(args[1]));

		try {

			NodeUtil nodeUtil = new NodeUtil();

			nodeUtil.registerNode();

			TimeUnit.SECONDS.sleep(30);

			nodeUtil.init();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}
}
