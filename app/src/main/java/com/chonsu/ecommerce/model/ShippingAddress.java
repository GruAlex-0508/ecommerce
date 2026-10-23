package com.chonsu.ecommerce.model;

public class ShippingAddress {
    private long id;
    private String addressName;
    private String addressLocation;


    public ShippingAddress(long id, String addressName, String addressLocation) {
        this.id = id;
        this.addressLocation = addressLocation;
        this.addressName = addressName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }
}
