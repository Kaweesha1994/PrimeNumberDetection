package com.dc.PrimeNumberDetection;

import java.util.Date;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		Bully bully = new Bully(args[0], (int) (nodeId < 0 ? nodeId : nodeId * -1), Integer.parseInt(args[1]));
	}

}
