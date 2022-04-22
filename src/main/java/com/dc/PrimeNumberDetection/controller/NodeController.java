package com.dc.PrimeNumberDetection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dc.PrimeNumberDetection.dto.NodeDto;
import com.dc.PrimeNumberDetection.service.NodeService;

@RestController
@RequestMapping("/prime-number-detection")
public class NodeController {

	@Autowired
	private NodeService nodeService;

	@GetMapping("/get-node-details")
	public ResponseEntity<NodeDto> getNodeDetails() {

		return new ResponseEntity<>(nodeService.getNodeDetails(), HttpStatus.OK);

	}

}
