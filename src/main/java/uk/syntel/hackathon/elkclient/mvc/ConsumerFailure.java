package uk.syntel.hackathon.elkclient.mvc;

import java.util.GregorianCalendar;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "kafkamsgfailures", type = "kfailures")
public class ConsumerFailure {

	public static final String INDEX_NAME = "kafkamsgfailures";
	public static final String INDEX_TYPE = "kfailures";
	
	@Id
	private String id;

	private String messageType;

	private String topic;

	private String groupId;

	private long offset;

	private String value;

	private String timestamp;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public ConsumerFailure() {
	}

	public ConsumerFailure(String messageType, String topic, String groupId, long offset, String value) {
		super();
		this.id = UUID.randomUUID().toString();
		this.messageType = messageType;
		this.topic = topic;
		this.groupId = groupId;
		this.offset = offset;
		this.value = value;
		this.timestamp = new GregorianCalendar().getTime().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ConsumerFailure [id=" + id + ", messageType=" + messageType + ", topic=" + topic + ", groupId="
				+ groupId + ", offset=" + offset + ", value=" + value + ", timestamp=" + timestamp + "]";
	}

}