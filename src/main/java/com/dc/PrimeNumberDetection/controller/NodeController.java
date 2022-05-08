package com.dc.PrimeNumberDetection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.AnnounceDto;
import com.dc.PrimeNumberDetection.dto.NodeDto;
import com.dc.PrimeNumberDetection.dto.RoleDto;
import com.dc.PrimeNumberDetection.service.NodeService;
import com.dc.PrimeNumberDetection.util.Counter;

@RestController
@RequestMapping("/prime-number-detection")
public class NodeController {

	@Autowired
	private NodeService nodeService;

	@GetMapping("/get-node-details")
	public ResponseEntity<NodeDto> getNodeDetails() {

		return new ResponseEntity<>(nodeService.getNodeDetails(), HttpStatus.OK);

	}

	@PostMapping("/announce")
	public ResponseEntity<NodeDto> Announce(@RequestBody AnnounceDto announceDto) {

		Bully.setCoordinatorName(announceDto.getCoordinator());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/proxy")
	public ResponseEntity<NodeDto> proxy(@RequestBody NodeDto nodeDto) {

		// create thread
		Counter.createThread();

		Counter.executorService.submit(new Counter(nodeDto.getNodeId()));

		Counter.executorService.shutdown();

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/response")
	public ResponseEntity<NodeDto> responseNode(@RequestBody NodeDto nodeDto) {

		nodeService.getNodeResponse(nodeDto);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/accepter")
	public ResponseEntity<NodeDto> acceptor(@RequestBody RoleDto roleDto) {

		System.out.println(roleDto.getRole());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/learner")
	public ResponseEntity<NodeDto> learner(@RequestBody RoleDto roleDto) {

		System.out.println(roleDto.getRole());

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/proposer")
	public ResponseEntity<NodeDto> proposer(@RequestBody RoleDto roleDto) {

		System.out.println(roleDto.getRole());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
