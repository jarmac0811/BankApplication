package com.luxoft.bankapp.service;

import java.util.concurrent.atomic.AtomicInteger;

public class BankServerMonitor implements Runnable {
	private static AtomicInteger counterAll;
	private static int awaiting;
	private static int POOL_SIZE;

	@Override
	public void run() {
		POOL_SIZE = BankServerThreaded.getPOOL_SIZE();
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
				e.printStackTrace();
			}
		}

	}

}
