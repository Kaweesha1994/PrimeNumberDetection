package com.dc.PrimeNumberDetection.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.AnnounceDto;
import com.dc.PrimeNumberDetection.dto.NodeDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NodeUtil {

	public List<NodeDto> getNodeDetails(List<Integer> portsOfNodes) {

		List<NodeDto> nodeDtos = new LinkedList<NodeDto>();

		for (Integer port : portsOfNodes) {

			String url = "http://localhost:" + port + "/prime-number-detection/get-node-details";

			System.out.println(url);

			RestTemplate restTemplate = new RestTemplate();

			NodeDto nodeDto = restTemplate.getForObject(url, NodeDto.class);

			nodeDtos.add(nodeDto);

		}

		return nodeDtos;

	}

	public List<Integer> getPortsOfNodes() {

		List<Integer> portsOfNodes = new LinkedList<Integer>();

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

			portsOfNodes.add((Integer) map2.get("Port"));

		}

		return portsOfNodes;

	}

	public void registerNode() {

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

	public Boolean readyForElection(List<Integer> portsOfNodes) {

		List<Boolean> coordinatorList = new LinkedList<Boolean>();
		List<Boolean> electionList = new LinkedList<Boolean>();

		List<NodeDto> nodeDtos = getNodeDetails(portsOfNodes);

		for (NodeDto nodeDto : nodeDtos) {

			coordinatorList.add(nodeDto.getCoordinator());
			electionList.add(nodeDto.getElection());

		}

		coordinatorList.add(Bully.getCoordinator());
		electionList.add(Bully.getElection());

		if (coordinatorList.contains(Boolean.TRUE) || electionList.contains(Boolean.TRUE)) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}

	}

	public List<Integer> getHigherNodes(List<NodeDto> nodeDtos) {

		List<Integer> higherNodes = new LinkedList<Integer>();

		for (NodeDto nodeDto : nodeDtos) {

			if (nodeDto.getNodeId() > Bully.getNodeId()) {

				higherNodes.add(nodeDto.getPort());

			}

		}

		return higherNodes;

	}

	public void announce() {

		List<Integer> portsOfAllNodes = getPortsOfNodes();

//		String data = "{'coordinator': " + Bully.getNodeName() + "}";

		AnnounceDto announceDto = new AnnounceDto();
		announceDto.setCoordinator(Bully.getNodeName());

		for (Integer port : portsOfAllNodes) {

			String url = "http://localhost:" + port + "/prime-number-detection/announce";

			System.out.println("announce url : " + url);

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.postForObject(url, announceDto, Object.class);

		}

	}

	public void election(List<Integer> higherNodes) {

		List<Integer> statusCodes;

		for (Integer higherNode : higherNodes) {

			String url = Constant.SERVER_URL + higherNode + Constant.PROXY_URL;

			System.out.println("proxy url : " + url);

			NodeDto nodeDto = new NodeDto();

			nodeDto.setNodeId(Bully.getNodeId());

			RestTemplate restTemplate = new RestTemplate();

			restTemplate.postForObject(url, nodeDto, Object.class);

		}

	}

}
