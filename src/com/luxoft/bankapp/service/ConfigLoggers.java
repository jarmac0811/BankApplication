package com.luxoft.bankapp.service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConfigLoggers {
	public ConfigLoggers() {

		try {
			Logger la = Logger.getLogger("LogA");
			Logger lb = Logger.getLogger("LogB");
			Logger lc = Logger.getLogger("LogC");
			try {
				la.addHandler(new FileHandler("db.log"));
				lb.addHandler(new FileHandler("clients.log"));
				lc.addHandler(new FileHandler("exceptions.log"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}

	}
}
