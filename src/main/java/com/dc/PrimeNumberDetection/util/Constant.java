package com.dc.PrimeNumberDetection.util;

public class Constant {

	public static Boolean wait = Boolean.TRUE;

	public static final String SERVER_URL = "http://localhost:";
	public static final String PROXY_URL = "/prime-number-detection/proxy";
	public static final String GET_NODE_RESPONSE_URL = "/prime-number-detection/response";
	public static final String REGISTER_NODE_URL = "http://localhost:8500/v1/agent/service/register";
	public static final String GET_SERVICES = "http://127.0.0.1:8500/v1/agent/services";
	public static final String GET_NODE_DETAILS = "/prime-number-detection/get-node-details";
	public static final String ANNOUNCE = "/prime-number-detection/announce";
	public static final String CHECK_FOR_HEALTH_URL = "http://localhost:8500/v1/agent/health/service/name/";
	public static final String DATA_ACCEPTER = "/prime-number-detection/accepter";
	public static final String DATA_LEARNER = "/prime-number-detection/learner";
	public static final String DATA_PROPOSER = "/prime-number-detection/proposer";

}
