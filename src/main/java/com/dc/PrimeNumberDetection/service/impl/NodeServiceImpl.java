package com.dc.PrimeNumberDetection.service.impl;

import org.springframework.stereotype.Service;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.NodeDto;
import com.dc.PrimeNumberDetection.service.NodeService;

@Service
public class NodeServiceImpl implements NodeService {

	@Override
	public NodeDto getNodeDetails() {

		NodeDto nodeDto = new NodeDto();

		nodeDto.setNodeId(Bully.getNodeId());
		nodeDto.setNodeName(Bully.getNodeName());
		nodeDto.setPort(Bully.getPort());
		nodeDto.setCoordinator(Bully.getCoordinator());
		nodeDto.setElection(Bully.getElection());

		return nodeDto;

	}

	@Override
	public void getNodeResponse(NodeDto nodeDto) {

		Integer incomingNodeId = nodeDto.getNodeId();

		if (Bully.getNodeId() > incomingNodeId) {
			Bully.setElection(Boolean.FALSE);
		}

	}

}
