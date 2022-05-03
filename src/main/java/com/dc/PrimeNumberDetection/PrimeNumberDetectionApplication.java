package com.dc.PrimeNumberDetection;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.NodeDto;
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

		long nodeId = timeMili + randomLong;

		System.out.println("node id before : " + nodeId);

		System.out.println("First arguement : " + args[0]);
		System.out.println("Second arguement : " + args[1]);

		if (nodeId < 0) {
			nodeId *= -1;

			System.out.println("node id : " + nodeId);
		}

		Bully bully = new Bully(args[0], (int) (nodeId), Integer.parseInt(args[1]));

		try {

			NodeUtil nodeUtil = new NodeUtil();

			nodeUtil.registerNode();

			List<Integer> portsOfAllNodes = nodeUtil.getPortsOfNodes();

			portsOfAllNodes.remove(Bully.getPort());

			List<NodeDto> nodeDtos = nodeUtil.getNodeDetails(portsOfAllNodes);

			int delay = new Random().nextInt(15);

			System.out.println("delay : " + delay);

			TimeUnit.SECONDS.sleep(delay);

			Boolean electionReady = nodeUtil.readyForElection(portsOfAllNodes);

			if (electionReady) {

				System.out.println("Starting election in : " + Bully.getNodeName());

				Bully.setElection(Boolean.TRUE);

				List<Integer> higherNodes = nodeUtil.getHigherNodes(nodeDtos);

				System.out.println("Higher nodes : " + higherNodes);

				if (higherNodes.size() == 0) {

					Bully.setCoordinator(Boolean.TRUE);
					Bully.setElection(Boolean.FALSE);

					nodeUtil.announce();

					System.out.println("Coordinator is : " + Bully.getNodeName());
					System.out.println("**********End of election**********************");

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}
}
