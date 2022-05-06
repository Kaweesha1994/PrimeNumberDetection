package com.dc.PrimeNumberDetection.service;

import com.dc.PrimeNumberDetection.dto.NodeDto;

public interface NodeService {

	NodeDto getNodeDetails();

	void getNodeResponse(NodeDto nodeDto);

}
