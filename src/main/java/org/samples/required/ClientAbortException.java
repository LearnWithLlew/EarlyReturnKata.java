package org.samples.required;

import java.io.IOException;

public class ClientAbortException extends IOException {
    public ClientAbortException(String message, int scRequestEntityTooLarge) {

    }

    public ClientAbortException(IOException e) {

    }
}
