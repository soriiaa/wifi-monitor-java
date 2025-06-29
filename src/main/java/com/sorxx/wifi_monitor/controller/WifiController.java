package com.sorxx.wifi_monitor.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sorxx.wifi_monitor.model.WifiInfo;
import com.sorxx.wifi_monitor.service.WifiService;

@RestController
public class WifiController {

	private final WifiService wifiService;

	public WifiController(WifiService wifiService) {
		this.wifiService = wifiService;
	}

	@GetMapping("/api/wifi-status")
	public WifiInfo getWifiStatus() {
		System.out.println("Llamada al endpoint getWifiStatus");
		WifiInfo wifiInfo = wifiService.getDatosWifi();
		return wifiInfo;
	}
	
	@GetMapping("/api/wifi-dispositivos")
	public List<String> getDispositivosWifi() {
		System.out.println("Llamada al endpoint getDispositivosWifi");
		List<String> listaDispositivos = wifiService.obtenerDispositivosConectados();
		return listaDispositivos;
	}
	
	@GetMapping("/")
    public String home() {
        return "Bienvenido al monitor de WiFi!";
    }

}
