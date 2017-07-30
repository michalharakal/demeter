package com.martingregor;

public interface InputEdgeCallback {

    boolean onGpioEdge(byte[] values);
}
