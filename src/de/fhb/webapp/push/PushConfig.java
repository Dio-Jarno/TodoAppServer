package de.fhb.webapp.push;

import java.io.File;

public class PushConfig {

	private String host;
	private int port;
	private File certFile;
	private String certPassword;
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public File getCertFile() {
		return certFile;
	}
	
	public String getCertPassword() {
		return certPassword;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setCertFile(File certFile) {
		this.certFile = certFile;
		if (!certFile.exists())
			throw new IllegalStateException("File " + certFile.getName() + " does not exist.");
	}
	
	public void setCertPassword(String certPassword) {
		this.certPassword = certPassword;
	}
	
	@Override
	public String toString() {
		return certFile.getName();
	}
}
