package com.sorxx.wifi_monitor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sorxx.wifi_monitor.model.WifiInfo;

@Service
public class WifiService {
	
	public List<String> obtenerDispositivosConectados() {
		
	    List<String> dispositivos = new ArrayList<>();

	    try {
	        Process proceso = Runtime.getRuntime().exec("nmap -sn 192.168.1.0/24");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

	        String linea;
	        while ((linea = reader.readLine()) != null) {
	            if (linea.startsWith("Nmap scan report for")) {
	                dispositivos.add(linea.replace("Nmap scan report for ", ""));
	            }
	        }

	        reader.close();
	        proceso.waitFor();

	    } catch (IOException | InterruptedException e) {
	        e.printStackTrace();
	    }

	    return dispositivos;
	}

	public WifiInfo getDatosWifi() {
	    WifiInfo info = new WifiInfo();

	    try {
	        // 1. Obtener datos de la interfaz WiFi con iwconfig
	        Process process = Runtime.getRuntime().exec("iwconfig");
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

	        String line;
	        while ((line = reader.readLine()) != null) {
	            line = line.trim();

	            if (line.startsWith("wlan") || line.startsWith("wl")) {
	                info.setInterfaceName(line.split(" ")[0].trim());
	            }

	            if (line.contains("ESSID:")) {
	                String ssid = line.substring(line.indexOf("ESSID:") + 6).replace("\"", "").trim();
	                info.setSsid(ssid);
	            }

	            if (line.contains("Access Point:")) {
	                String bssid = line.substring(line.indexOf("Access Point:") + 13).trim();
	                info.setBssid(bssid);
	            }

	            if (line.contains("Signal level=")) {
	                int signalDbm = parseSignalDbm(line);
	                info.setSignalStrength(signalDbm);
	                // Convertir dBm a calidad (aprox)
	                info.setLinkQuality(dbmToQuality(signalDbm));
	            }

	            if (line.contains("Bit Rate=")) {
	                int rate = parseRateLinux(line);
	                info.setRxRate(rate);
	                info.setTxRate(rate); // Si no se puede diferenciar, igual
	            }
	        }

	        // 2. Obtener IP y máscara con ip addr show
	        process = Runtime.getRuntime().exec("ip addr show " + info.getInterfaceName());
	        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

	        while ((line = reader.readLine()) != null) {
	            line = line.trim();
	            if (line.startsWith("inet ")) {
	                String[] parts = line.split(" ");
	                String ipCidr = parts[1];
	                String ip = ipCidr.split("/")[0];
	                info.setIp(ip);
	                // Máscara de red en CIDR -> convertir a notación decimal
	                String netmask = cidrToNetmask(ipCidr.split("/")[1]);
	                info.setNetmask(netmask);
	            }
	        }

	        // 3. Obtener puerta de enlace con 'ip route'
	        process = Runtime.getRuntime().exec("ip route");
	        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith("default via")) {
	                String[] parts = line.split(" ");
	                info.setGateway(parts[2]);
	                break;
	            }
	        }

	        // 4. Obtener MAC con 'cat /sys/class/net/[interface]/address'
	        process = Runtime.getRuntime().exec("cat /sys/class/net/" + info.getInterfaceName() + "/address");
	        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String mac = reader.readLine();
	        info.setMacAddress(mac);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return info;
	}

	private int parseSignalDbm(String line) {
	    try {
	        // Ejemplo: "Signal level=-45 dBm"
	        int idx = line.indexOf("Signal level=");
	        if (idx != -1) {
	            String sub = line.substring(idx + 13);
	            String value = sub.split(" ")[0].trim();
	            return Integer.parseInt(value);
	        }
	    } catch (Exception ignored) {}
	    return -1;
	}

	private int dbmToQuality(int dbm) {
	    // Aproximado, para -100 dBm calidad 0, para -50 dBm calidad 100
	    if (dbm <= -100) return 0;
	    else if (dbm >= -50) return 100;
	    else return 2 * (dbm + 100);
	}

	private int parseRateLinux(String line) {
	    try {
	        // Ejemplo: "Bit Rate=54 Mb/s"
	        int idx = line.indexOf("Bit Rate=");
	        if (idx != -1) {
	            String sub = line.substring(idx + 9);
	            String rateStr = sub.split(" ")[0].trim();
	            return Integer.parseInt(rateStr);
	        }
	    } catch (Exception ignored) {}
	    return -1;
	}

	private String cidrToNetmask(String cidrStr) {
	    try {
	        int cidr = Integer.parseInt(cidrStr);
	        int mask = 0xffffffff << (32 - cidr);
	        int value = mask;
	        return String.format("%d.%d.%d.%d",
	                (value >> 24 & 0xff),
	                (value >> 16 & 0xff),
	                (value >> 8 & 0xff),
	                (value & 0xff));
	    } catch (Exception e) {
	        return "255.255.255.0";
	    }
	}

}