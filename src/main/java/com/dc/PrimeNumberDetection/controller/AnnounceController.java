package com.dc.PrimeNumberDetection.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dc.PrimeNumberDetection.bully.Bully;
import com.dc.PrimeNumberDetection.dto.AnnounceDto;
import com.dc.PrimeNumberDetection.dto.NodeDto;

@RestController
@RequestMapping("/")
public class AnnounceController {

	@PostMapping("/announce")
	public ResponseEntity<NodeDto> Announce(@RequestBody AnnounceDto announceDto) {

		Bully.setCoordinatorName(announceDto.getCoordinator());

		return new ResponseEntity<>(HttpStatus.OK);

	}

}
