package com.jws.medicalfile.api.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}