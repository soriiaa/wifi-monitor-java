package com.sorxx.wifi_monitor.model;

public class WifiInfo {

	private String ssid; // Nombre de la red
	private String bssid; // Dirección MAC del router
	private String ip; // IP asignada al dispositivo
	private String gateway; // Puerta de enlace
	private String netmask; // Máscara de red
	private String macAddress; // MAC de tu interfaz
	private int signalStrength; // Intensidad de señal (dBm)
	private int linkQuality; // Calidad del enlace (0-100)
	private int txRate; // Velocidad de transmisión (Mbps)
	private int rxRate; // Velocidad de recepción (Mbps)
	private String interfaceName; // Nombre del adaptador (ej: wlan0)
	private boolean connected; // ¿Está conectado?
	private String frequency; // Frecuencia (2.4 GHz, 5 GHz)
	private String security; // Tipo de seguridad (WPA2, WPA3...)
	private String countryCode; // Código de país (por el canal permitido)
	private String channel; // Canal de Wi-Fi
	private String mode; // Modo de operación (Managed, Monitor...)
	
	public WifiInfo() {

	}
	
	public WifiInfo(String ssid, String ip) {
		this.ssid = ssid;
		this.ip = ip;
	}
	
	public WifiInfo(String ssid, String bssid, String ip, String gateway, String netmask, String macAddress,
			int signalStrength, int linkQuality, int txRate, int rxRate, String interfaceName, boolean connected,
			String frequency, String security, String countryCode, String channel, String mode) {
		
		this.ssid = ssid;
		this.bssid = bssid;
		this.ip = ip;
		this.gateway = gateway;
		this.netmask = netmask;
		this.macAddress = macAddress;
		this.signalStrength = signalStrength;
		this.linkQuality = linkQuality;
		this.txRate = txRate;
		this.rxRate = rxRate;
		this.interfaceName = interfaceName;
		this.connected = connected;
		this.frequency = frequency;
		this.security = security;
		this.countryCode = countryCode;
		this.channel = channel;
		this.mode = mode;
	}
	
	public String getSsid() {
	    return ssid;
	}

	public void setSsid(String ssid) {
	    this.ssid = ssid;
	}

	public String getBssid() {
	    return bssid;
	}

	public void setBssid(String bssid) {
	    this.bssid = bssid;
	}

	public String getIp() {
	    return ip;
	}

	public void setIp(String ip) {
	    this.ip = ip;
	}

	public String getGateway() {
	    return gateway;
	}

	public void setGateway(String gateway) {
	    this.gateway = gateway;
	}

	public String getNetmask() {
	    return netmask;
	}

	public void setNetmask(String netmask) {
	    this.netmask = netmask;
	}

	public String getMacAddress() {
	    return macAddress;
	}

	public void setMacAddress(String macAddress) {
	    this.macAddress = macAddress;
	}

	public int getSignalStrength() {
	    return signalStrength;
	}

	public void setSignalStrength(int signalStrength) {
	    this.signalStrength = signalStrength;
	}

	public int getLinkQuality() {
	    return linkQuality;
	}

	public void setLinkQuality(int linkQuality) {
	    this.linkQuality = linkQuality;
	}

	public int getTxRate() {
	    return txRate;
	}

	public void setTxRate(int txRate) {
	    this.txRate = txRate;
	}

	public int getRxRate() {
	    return rxRate;
	}

	public void setRxRate(int rxRate) {
	    this.rxRate = rxRate;
	}

	public String getInterfaceName() {
	    return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
	    this.interfaceName = interfaceName;
	}

	public boolean isConnected() {
	    return connected;
	}

	public void setConnected(boolean connected) {
	    this.connected = connected;
	}

	public String getFrequency() {
	    return frequency;
	}

	public void setFrequency(String frequency) {
	    this.frequency = frequency;
	}

	public String getSecurity() {
	    return security;
	}

	public void setSecurity(String security) {
	    this.security = security;
	}

	public String getCountryCode() {
	    return countryCode;
	}

	public void setCountryCode(String countryCode) {
	    this.countryCode = countryCode;
	}

	public String getChannel() {
	    return channel;
	}

	public void setChannel(String channel) {
	    this.channel = channel;
	}

	public String getMode() {
	    return mode;
	}

	public void setMode(String mode) {
	    this.mode = mode;
	}


}
