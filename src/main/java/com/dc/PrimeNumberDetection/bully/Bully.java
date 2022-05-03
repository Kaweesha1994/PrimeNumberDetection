package com.dc.PrimeNumberDetection.bully;

public class Bully {

	private static String nodeName;
	private static Integer nodeId;
	private static Integer port;
	private static Boolean election;
	private static Boolean coordinator;
	private static String coordinatorName;

	public Bully(String nodeName, Integer nodeId, Integer port) {
		super();
		this.nodeName = nodeName;
		this.nodeId = nodeId;
		this.port = port;
		this.election = Boolean.FALSE;
		this.coordinator = Boolean.FALSE;
	}

	public static String getNodeName() {
		return nodeName;
	}

	public static Integer getNodeId() {
		return nodeId;
	}

	public static Integer getPort() {
		return port;
	}

	public static Boolean getElection() {
		return election;
	}

	public static Boolean getCoordinator() {
		return coordinator;
	}

	public static void setNodeName(String nodeName) {
		Bully.nodeName = nodeName;
	}

	public static void setNodeId(Integer nodeId) {
		Bully.nodeId = nodeId;
	}

	public static void setPort(Integer port) {
		Bully.port = port;
	}

	public static void setElection(Boolean election) {
		Bully.election = election;
	}

	public static void setCoordinator(Boolean coordinator) {
		Bully.coordinator = coordinator;
	}

	public static String getCoordinatorName() {
		return coordinatorName;
	}

	public static void setCoordinatorName(String coordinatorName) {
		Bully.coordinatorName = coordinatorName;
	}

}
