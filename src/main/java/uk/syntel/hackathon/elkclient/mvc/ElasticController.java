package uk.syntel.hackathon.elkclient.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Elastic Client service", description="Elastic Client Operations to save Consumer Failures")
@RestController
public class ElasticController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ElasticService service;

	@ApiOperation(value = "Save Record in Elastic Search",response = ResponseEntity.class)
	@PostMapping("/createLog")
	public ResponseEntity<?> createLog(@RequestBody ConsumerFailure consumerFailure) {
		
		logger.info("Received failure Request : "+consumerFailure);
		service.createLog(consumerFailure);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}