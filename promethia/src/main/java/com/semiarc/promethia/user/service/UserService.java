package com.semiarc.promethia.user.service;

import com.semiarc.promethia.user.dto.UserProfileResponse;

public interface UserService {

    UserProfileResponse getCurrentUserProfile(String email);
}