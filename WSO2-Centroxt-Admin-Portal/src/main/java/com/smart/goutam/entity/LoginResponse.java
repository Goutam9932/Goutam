package com.smart.goutam.entity;

public class LoginResponse {
    private boolean status;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

	public LoginResponse(Long id, String userName, String firstName, String lastName) {
		// TODO Auto-generated constructor stub
	}
}
