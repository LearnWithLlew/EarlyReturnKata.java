package org.samples.required;
import java.io.IOException;
import java.nio.charset.Charset;

public class Parameters {
    public void setLimit(int maxParameterCount) {
    }

    public boolean getUseBodyEncodingForURI() {
        return false;
    }
    public boolean isParseBodyMethod(Object method){
        return false;
    }

    public void setCharset(Charset charset) {
    }

    public void setQueryStringCharset(Charset charset) {
    }

    public void handleQueryParameters() {
    }

    public int getMaxPostSize() {
        return 0;
    }

    public String getString(String s) {
        return null;
    }

    public Parameters getLogger() {
        return null;
    }

    public boolean isDebugEnabled() {
        return false;
    }

    public void debug(String message) {
    }

    public void processParameters(byte[] formData, int i, int length) {
    }

    public void debug(String string, IOException e) {
    }

    public Parameters getCoyoteResponse() {
        return null;
    }

    public void action(Object closeNow, Object o) {
    }
}
