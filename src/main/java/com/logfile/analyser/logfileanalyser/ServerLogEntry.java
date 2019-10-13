package com.logfile.analyser.logfileanalyser;

public class ServerLogEntry {
	private String id;
	private String state;
	private String type;
	private String host;
	private Long timestamp;
	private Boolean alert;

	public ServerLogEntry(final String id, final String state, final Long timestamp, final String type, final String host,
			final Boolean alert) {
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
		this.type = type;
		this.host = host;
		this.alert = alert;
	}

	public ServerLogEntry(final String id, final String state, final long timestamp, final String type, final String host) {
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
		this.type = type;
		this.host = host;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	@Override
	public String toString() {
		return "ServerEvent{" + "id='" + id + '\'' + ", state='" + state + '\'' + ", timestamp='" + timestamp + '\''
				+ ", type='" + type + '\'' + ", host='" + host + '\'' + ", alert=" + alert + '}';
	}
}
