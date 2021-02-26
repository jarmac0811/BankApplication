package com.myapp.bankapp.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankServerMonitor implements Runnable {
	private  AtomicInteger counterAll;
	private  int awaiting;
	private  int POOL_SIZE;
	private static final Logger logger = Logger.getLogger(BankServerMonitor.class.getName());

	@Override
	public void run() {
		POOL_SIZE = new BankServerThreaded().getPOOL_SIZE();
		while (true) {
			counterAll = BankServerThreaded.getCounter();
			if (counterAll.get() > POOL_SIZE) {

				awaiting = counterAll.get() - POOL_SIZE;
			}
			if (counterAll.get() <= POOL_SIZE) {
				awaiting = 0;
			}
			System.out.println("in monitor awaiting: " + awaiting);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}

	}

}
