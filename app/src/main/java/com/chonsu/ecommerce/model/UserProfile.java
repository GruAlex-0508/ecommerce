package com.chonsu.ecommerce.model;

import androidx.annotation.DrawableRes;

public class UserProfile {
    private String name;
    private String phone;
    @DrawableRes
    private int avatarRes;

    public UserProfile(String name, String phone, @DrawableRes int avatarRes) {
        this.name = name;
        this.phone = phone;
        this.avatarRes = avatarRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAvatarRes() {
        return avatarRes;
    }

    public void setAvatarRes(@DrawableRes int avatarRes) {
        this.avatarRes = avatarRes;
    }
}
