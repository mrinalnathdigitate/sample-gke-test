package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("test")
public class TestRest {
	
	@GetMapping
	public String test() {
		return "hello" + System.currentTimeMillis();
	}

	@PostMapping(value = "upload", headers = "Content-Type= multipart/form-data")
	public String fileUpload(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("got file with name {}", file.getOriginalFilename());
		LOGGER.info("got file with name {}", file.getOriginalFilename());
		
		return file.getOriginalFilename();
	}
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TestRest.class);
}
