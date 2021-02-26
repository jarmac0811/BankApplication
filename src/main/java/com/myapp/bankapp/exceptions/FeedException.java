package com.myapp.bankapp.exceptions;

public class FeedException extends RuntimeException {
private String message;
    public FeedException(String message) {
         this.message=message;
    }
   @Override
   public String toString(){
	   return message;
   }
}
