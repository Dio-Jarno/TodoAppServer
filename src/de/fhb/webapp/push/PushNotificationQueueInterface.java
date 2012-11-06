package de.fhb.webapp.push;

public interface PushNotificationQueueInterface {
	/**
	 * @param app The app (use {@link App#getApp()}) that triggers the push.
	 * @param deviceToken Push Token
	 * @param message Message to be displayed in the popup. If <code>null</code> then no push is performed.
	 * @param type Any of the <code>PushNotification.TYPE_...</code> constants.
	 */
	public boolean pushNotification(final String app, String deviceToken, String message, int type);
	
	public void pushNotificationDelayed(final String app, String deviceToken, String message, int type, long delayMillis);

	public boolean isEmpty();
	
}
