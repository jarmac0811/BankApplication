package com.myapp.bankapp.service;

import java.lang.reflect.Field;



public class TestService {
	public static boolean isEquals(Object o1, Object o2) {
		Field[] fields = o1.getClass().getDeclaredFields();
		Field[] fields2 = o2.getClass().getDeclaredFields();
		if (fields.length != fields2.length)
			return false;
		for(int i=0;i<fields.length;i++){
		boolean isAnnotated = fields[i].isAnnotationPresent(NoDB.class);
		System.out.println(isAnnotated);
		if (!isAnnotated) {
			fields[i].setAccessible(true);
			fields2[i].setAccessible(true);
			try {
				if(!fields[i].get(o1).equals(fields2[i].get(o2)))
					return false;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		}
		return true;
	}
}
