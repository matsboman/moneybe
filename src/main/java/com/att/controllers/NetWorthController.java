package com.att.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.att.common.ResponseFactory;
import com.att.dao.NetWorthRepository;
import com.att.model.NetWorth;

@RestController
public class NetWorthController {

	private static final Logger logger = LoggerFactory.getLogger(NetWorthController.class);

	@Autowired
	NetWorthRepository netWorthRepository;

	@DeleteMapping(path = "/networth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteAllNetWorths(HttpServletRequest request) {
		netWorthRepository.deleteAll();
		return ResponseFactory.response(HttpStatus.OK);
	}

	@DeleteMapping(path = "/networth/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteNetWorthById(@PathVariable int id, HttpServletRequest request) {
		Optional<NetWorth> networth = netWorthRepository.findByNetWorthID(id);
		if (networth == null) {
			logger.info("NetWorth Id not found.");
			return ResponseFactory.response(HttpStatus.NOT_FOUND);
		}
		netWorthRepository.deleteById(id);
		return ResponseFactory.response(HttpStatus.OK);
	}

	@GetMapping(path = "/networth", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllNetWorths(HttpServletRequest request) {
		return new ResponseEntity<>(netWorthRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping(path = "/networth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postNetWorths(@RequestBody List<NetWorth> networths, HttpServletRequest request) {
		for (NetWorth networth : networths) {
			Optional<NetWorth> networthInDb = netWorthRepository.findByNetWorthID(networth.getNetWorthID());
			if (networthInDb.isPresent()) {
				logger.info("NetWorth already exists.");
				return ResponseFactory.response("NetWorth already exists", HttpStatus.CONFLICT);
			}
		}
		netWorthRepository.saveAll(networths);
		return ResponseFactory.response("NetWorths created", HttpStatus.OK);
	}

	@PutMapping(path = "/networth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> putNetWorths(@RequestBody List<NetWorth> networths, HttpServletRequest request) {
		List<NetWorth> dbList = new ArrayList<>();
		networths.forEach(networth -> {
			Optional<NetWorth> networthInDb = netWorthRepository.findByNetWorthID(networth.getNetWorthID());
			if (networthInDb.isPresent()) {
				networthInDb.get().updateFrom(networth);
				dbList.add(networthInDb.get());
			} else {
				dbList.add(networth);
			}
		});
		netWorthRepository.saveAll(dbList);
		return ResponseFactory.response(HttpStatus.OK);
	}
}
