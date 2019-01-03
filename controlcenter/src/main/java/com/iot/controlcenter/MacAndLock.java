package com.iot.controlcenter;

import java.util.concurrent.locks.Lock;

public class MacAndLock {
    private String macAddress;
    private Lock lock;

    public MacAndLock(String macAddress, Lock lock) {
        this.macAddress = macAddress;
        this.lock = lock;
    }

    public MacAndLock() {
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Lock getLock() {
        return lock;
    }
}
