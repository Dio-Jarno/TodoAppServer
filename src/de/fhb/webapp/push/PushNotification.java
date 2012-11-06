package de.fhb.webapp.push;

public class PushNotification 
{
	public static final int TYPE_UNDEFINED = 0;
	public static final int TYPE_MBP_AVAILABLE = 1;
	public static final int TYPE_FLIGHT_DELAY_DEPARTURE = 2;
	public static final int TYPE_FLIGHT_DELAY_ARRIVAL = 3;
	public static final int TYPE_GATE_ADJUSTMENT_DEPARTURE = 4;
	public static final int TYPE_GATE_ADJUSTMENT_ARRIVAL = 5;
	public static final int TYPE_STATUS_CHANGED = 6;
	public static final int TYPE_BAGGAGE_CLAIM_ADJUSTMENT = 7;
	public static final int TYPE_OFFER_AVAILABLE = 8;
	public static final int TYPE_REVIEW = 9;
	public static final int TYPE_SYSTEM_INFORMATION = 10;
	
	public static final int TYPE_ORDER_PROCESSING = 1001;
	public static final int TYPE_ORDER_CANCELLED = 1002;
	public static final int TYPE_ORDER_ARRIVED_AT_DELIVERY_STATION = 1003;
	public static final int TYPE_ORDER_OUT_FOR_DELIVERY = 1004;
	
	
	/**
	 * iPhone must display buttons "Yes" and "No, Thanks"
	 */
	public static final int TYPE_ORDER_MISSED_DELIVERY = 1005;
	public static final int TYPE_ORDER_READY_FOR_PICKUP = 1006;
	public static final int TYPE_ORDER_COMPLETE = 1007;
	
	/**
	 * iPhone must display buttons "Feedback" and "Later"
	 */
	public static final int TYPE_ORDER_SURVEY = 1008;
	
	/**
	 * Should only be received inApp
	 */
	public static final int TYPE_PROMOTION_GENERALDISCOUNT = 1009;
	
	
	protected String app;
	protected String deviceToken;
	protected String message;
	protected int type = TYPE_UNDEFINED;
	
	public PushNotification()
	{
	}

	/**
	 * @param app App name
	 * @param deviceToken Push Token
	 * @param message Message to be displayed in the popup.
	 * @param type Any of the <code>PushNotification.TYPE_...</code> constants.
	 */
	public PushNotification(String app, String deviceToken, String message, int type)
	{
		setApp(app);
		setDeviceToken(deviceToken);
		setMessage(message);
		setType(type);
	}
	
	public String getApp() {
		return app;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	public String getDeviceToken() 
	{
		return deviceToken;
	}
	
	public void setDeviceToken(String deviceToken) 
	{
		this.deviceToken = deviceToken;
	}
	
	public String getMessage() 
	{
		return message;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "[push type " + type + " message \""+message+"\" to device \""+deviceToken+"\"]";
	}
}
