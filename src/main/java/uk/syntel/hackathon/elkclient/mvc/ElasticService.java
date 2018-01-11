package uk.syntel.hackathon.elkclient.mvc;

import java.net.InetAddress;
import java.util.UUID;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticService {

	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void createLog(ConsumerFailure consumerFailure) {
		createlog(consumerFailure);
	}

	private IndexRequest createIndex(ConsumerFailure f) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(f);

		IndexRequest indexRequest = new IndexRequest(ConsumerFailure.INDEX_NAME, ConsumerFailure.INDEX_TYPE,
				UUID.randomUUID().toString());
		indexRequest.source(jsonInString, XContentType.JSON);

		return indexRequest;
	}

	private void createlog(ConsumerFailure f) {
		PreBuiltTransportClient preBuiltTransportClient = null;
		try {

			Settings esSettings = Settings.builder().put("cluster.name", EsClusterName).build();
			preBuiltTransportClient = new PreBuiltTransportClient(esSettings);
			preBuiltTransportClient.addTransportAddress(new TransportAddress(InetAddress.getByName(EsHost), EsPort));

			BulkRequest bulk = new BulkRequest();
			bulk.add(createIndex(f));

			BulkResponse rs = preBuiltTransportClient.bulk(bulk).actionGet();

			BulkItemResponse[] rss = rs.getItems();

			for (int i = 0; i < rss.length; i++) {
               logger.info(rss[i].getResponse().getId().toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preBuiltTransportClient != null) {
				preBuiltTransportClient.close();
			}
		}

	}

}