package com.foo.bar.admin;

import com.foo.bar.client.AdminClient;
import com.foo.bar.client.ClientInterface;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class StoreAdmin {
    private ClientInterface itsInterface;
    private LineNumberReader itsInput;

    public StoreAdmin() {
        itsInterface = new AdminClient();
        itsInput = new LineNumberReader(new InputStreamReader(System.in));

    }

    public static void main(String [] argv) {
        StoreAdmin sm = new StoreAdmin();
    }
}
