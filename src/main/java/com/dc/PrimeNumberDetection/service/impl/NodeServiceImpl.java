package com.dc.PrimeNumberDetection.service.impl;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.service.NodeService;

public class NodeServiceImpl implements NodeService {

	@Override
	public void getNodeDetails() {

		System.out.println("bully name : " + Bully.getNodeName());
		System.out.println("bully node id : " + Bully.getNodeId());
		System.out.println("bully port : " + Bully.getPort());

	}

}
