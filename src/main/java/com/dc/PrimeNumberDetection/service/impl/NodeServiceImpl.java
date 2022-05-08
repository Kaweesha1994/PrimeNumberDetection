package com.dc.PrimeNumberDetection.service.impl;

import org.springframework.stereotype.Service;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.NodeDto;
import com.dc.PrimeNumberDetection.service.NodeService;
import com.dc.PrimeNumberDetection.util.Constant;
import com.dc.PrimeNumberDetection.util.NodeUtil;

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

			NodeUtil nodeUtil = new NodeUtil();

			Constant.wait = Boolean.FALSE;

			try {

				nodeUtil.init();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Bully.setElection(Boolean.FALSE);
		}

	}

}
