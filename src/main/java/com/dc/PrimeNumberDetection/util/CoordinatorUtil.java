package com.dc.PrimeNumberDetection.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.RoleDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CoordinatorUtil {

	public void masterWork() {

		List<String> activeNodes = checkActiveNodes();

		Map<String, String> roles = decideRoles(activeNodes);

		Map<String, List<String>> combined = informRoles(roles);

		updateServiceRegistry(combined);
	}

	public List<String> checkActiveNodes() {

		List<String> registeredNodes = new LinkedList<String>();

		String url = Constant.GET_SERVICES;

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> object = restTemplate.getForEntity(url, Object.class);

		Map<String, Object> o = (LinkedHashMap<String, Object>) object.getBody();

		ObjectMapper mapper = new ObjectMapper();

		Set<String> healthStatus = new HashSet<String>();

		for (String key : o.keySet()) {
			System.out.println(key);

			Map<String, Object> map2 = mapper.convertValue(o.get(key), Map.class);

			System.out.println(map2.get("Service"));

			registeredNodes.add((String) map2.get("Service"));
			registeredNodes.remove(Bully.getNodeName());

			NodeUtil nodeUtil = new NodeUtil();

			for (String nodeName : registeredNodes) {

				if (nodeUtil.checkHealthOfService(nodeName).equals("passing")) {
					healthStatus.add(nodeName);
				}

			}

		}

		System.out.println("the active nodes are : " + healthStatus);

		return healthStatus.stream().collect(Collectors.toList());

	}

	public Map<String, String> decideRoles(List<String> activeNodes) {

		Map<String, String> roles = new HashMap<String, String>();

		String node = activeNodes.get(0);
		String role = "Accepter";

		String key = node;
		String value = role;

		roles.put(key, value);

		String learner = activeNodes.get(1);

		roles.put(learner, "Learner");

		for (int i = 2; i < activeNodes.size(); i++) {
			String node1 = activeNodes.get(i);
			String role1 = "Proposer";

			String key1 = node1;
			String value1 = role1;

			roles.put(key1, value1);
		}

		System.out.println("Roles : " + roles);

		return roles;

	}

	public Map<String, List<String>> informRoles(Map<String, String> roles) {
		NodeUtil nodeUtil = new NodeUtil();

		Map<String, Integer> portsMap = nodeUtil.getPortsOfNodesMap();

		portsMap.remove(Bully.getNodeName());

		Map<String, List<String>> combined = new HashMap<String, List<String>>();

		for (String key : roles.keySet()) {

			// key as port and value as role name

			List<String> list = new ArrayList<String>();
			list.add(roles.get(key));
			list.add(Integer.toString(portsMap.get(key)));

			combined.put(key, list);

		}

		System.out.println("Combined : " + combined);

		RoleDto dataAccepter = new RoleDto();
		dataAccepter.setRole("acceptor");
		RoleDto dataLearner = new RoleDto();
		dataLearner.setRole("learner");
		RoleDto dataProposer = new RoleDto();
		dataProposer.setRole("proposer");

		for (String key : combined.keySet()) {

			if (combined.get(key).get(0).equals("Accepter")) {

				String url = Constant.SERVER_URL + combined.get(key).get(1) + Constant.DATA_ACCEPTER;

				System.out.println("accepter url : " + url);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.postForObject(url, dataAccepter, Object.class);

			} else if (combined.get(key).get(0).equals("Learner")) {

				String url = Constant.SERVER_URL + combined.get(key).get(1) + Constant.DATA_LEARNER;

				System.out.println("learner url : " + url);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.postForObject(url, dataLearner, Object.class);

			} else if (combined.get(key).get(0).equals("Proposer")) {

				String url = Constant.SERVER_URL + combined.get(key).get(1) + Constant.DATA_PROPOSER;

				System.out.println("proposer url : " + url);

				RestTemplate restTemplate = new RestTemplate();

				restTemplate.postForObject(url, dataProposer, Object.class);

			}

		}

		return combined;

	}

	public void updateServiceRegistry(Map<String, List<String>> roles) {

		String url = Constant.REGISTER_NODE_URL;

		for (String key : roles.keySet()) {

			RestTemplate restTemplate = new RestTemplate();

//			String data = "\"{Name\": \"" + key + "\",\r\n" + "            \"ID\": \"" + getNodeId(key) + "\",\r\n"
//					+ "            \"port\": " + Integer.valueOf(roles.get(key).get(1)) + ",\r\n"
//					+ "            \"peta\": {\"Role\": \"" + roles.get(key).get(0) + "\"},\r\n"
//					+ "            \"check\": {\r\n" + "                \"name\": \"Check Counter health "
//					+ roles.get(key).get(1) + "\" ,\r\n" + "                \"tcp\": \"localhost:"
//					+ Integer.valueOf(roles.get(key).get(1)) + "\",\r\n" + "                \"interval\": \"10s\",\r\n"
//					+ "                \"timeout\": \"1s\"\r\n" + "            }}";

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Name", key);
			jsonObject.put("ID", Integer.valueOf(getNodeId(key)));
			jsonObject.put("port", Integer.valueOf(roles.get(key).get(1)));

			JSONObject meta = new JSONObject();
			meta.put("Role", roles.get(key).get(0));

			jsonObject.put("meta", meta);

			JSONObject check = new JSONObject();
			check.put("name", "Check Counter health " + roles.get(key).get(1));
			check.put("tcp", "localhost:" + roles.get(key).get(1));
			check.put("internal", "10s");
			check.put("timeout", "1s");

			jsonObject.put("check", check);

			System.out.println(jsonObject);

			restTemplate.put(url, jsonObject);

		}

	}

	public Integer getNodeId(String nodeName) {

		String url = Constant.GET_SERVICES;

		Integer nodeId = 0;

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> object = restTemplate.getForEntity(url, Object.class);

		Map<String, Object> o = (LinkedHashMap<String, Object>) object.getBody();

		ObjectMapper mapper = new ObjectMapper();

		for (String key : o.keySet()) {
			System.out.println(key);

			Map<String, Object> map2 = mapper.convertValue(o.get(key), Map.class);

			System.out.println(map2.get("Service"));
			System.out.println(map2.get("Port"));

			if (map2.get("Service").equals(nodeName)) {

				nodeId = Integer.valueOf((String) map2.get("ID"));

			}

		}

		return nodeId;

	}
}
