package de.fhb.webapp.push;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;


public class PushNotificationQueue extends AbstractProducerConsumer<PushNotification> implements PushNotificationQueueInterface {
    private static final String TYPE = "type";
    
	protected PushConfig defaultPushConfig;
	protected Map<String, PushConfig> pushConfigs;
    
	public PushNotificationQueue()
	{
		super(1000, false);
	}
	
	public void setQueueSize(int queueSize)
	{
		super.ringBuffer.setSize(queueSize);
	}
	
	public void setDefaultPushConfig(PushConfig defaultPushConfig) {
		this.defaultPushConfig = defaultPushConfig;
	}
	
	public void setPushConfigs(Map<String, PushConfig> pushConfigs) {
		this.pushConfigs = pushConfigs;
	}
	
	public void init() {
		if (defaultPushConfig == null)
			throw new IllegalStateException("defaultPushConfig is not set");
	}
	
	public PushConfig getPushConfig(String app) {
		PushConfig config = (pushConfigs == null) ? null : pushConfigs.get(app);
		return (config == null) ? defaultPushConfig : config;
	}
	
	@Override
	public boolean pushNotification(String app, String deviceToken, String message, int type)
	{
		if (isEmpty(message))
			return false;
		return pushOne(new PushNotification(app, deviceToken, message, type));
	}

	private static boolean isEmpty(String s) {
		return (s == null) || ("".equals(s));
	}

	@Override
	public void pushNotificationDelayed(final String app, final String deviceToken, final String message, final int type, final long delayMillis) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(delayMillis);
				} catch (InterruptedException e) { }
				pushNotification(app, deviceToken, message, type);
			}
			
		}).start();
	}

	@Override
	protected synchronized boolean onProcess() 
	{
		try
		{
			PushNotification pushNotification = popOne();
			PushConfig pushConfig = getPushConfig(pushNotification.getApp());
			
			pushLibNotnoop(pushNotification, pushConfig);
			
		} 
		catch (Exception e) 
		{
		} 		
		
		// don't wait, get next in queue, if there is
		return false;
	}
	
	protected void pushLibNotnoop(PushNotification pushNotification, PushConfig pushConfig) throws Exception
	{
		InputStream is = new FileInputStream(pushConfig.getCertFile());
		ApnsService apnService = APNS.newService()
			.withCert(is, pushConfig.getCertPassword())
			.withGatewayDestination(pushConfig.getHost(), pushConfig.getPort())
			.build();

		String payload = PayloadBuilder.newPayload()
			.alertBody(pushNotification.getMessage())
			.sound("default")
			.customField(TYPE, pushNotification.getType())
			.build();
        
		apnService.push(pushNotification.getDeviceToken(), payload);
		is.close();
	}
}
