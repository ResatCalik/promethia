package com.semiarc.promethia.auth.service;

import com.semiarc.promethia.auth.dto.AuthResponse;
import com.semiarc.promethia.auth.dto.LoginRequest;
import com.semiarc.promethia.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
