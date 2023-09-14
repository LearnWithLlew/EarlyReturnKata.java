package org.samples.required;

import jakarta.servlet.http.Part;
import org.samples._2_Request;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

public class RequestBase {
    /**
     * Post data buffer.
     */
    protected static final int CACHED_POST_LEN = 8192;
    protected Parameters response;
    protected _2_Request coyoteRequest;
    protected Parameters sm;
    /**
     * Using stream flag.
     */
    protected boolean usingInputStream = false;
    protected boolean usingReader = false;
    protected boolean parametersParsed = false;
    protected byte[] postData = null;
    protected Exception parametersParseException = null;
    /**
     * The parts, if any, uploaded with this request.
     */
    protected Collection<Part> parts = null;
    /**
     * The exception thrown, if any when parsing the parts.
     */
    protected Exception partsParseException = null;
    protected Parameters connector;

    protected byte[] readChunkedPostBody() throws IOException {
        return new byte[0];
    }

    protected String getHeader(String s) {
        return null;
    }

    protected void checkSwallowInput() {

    }

    protected void readPostBodyFully(byte[] formData, int len) throws IOException {
    }

    protected int getContentLength() {
        return 0;
    }

    protected Object getMethod() {
        return null;
    }

    protected Context getContext() {
        return null;
    }

    protected Parameters getConnector() {
        return null;
    }

    protected void parseParts() {
    }

    protected String getContentType() {
        return null;
    }

    protected Charset getCharset() {
        return null;
    }

    protected int getMaxParameterCount() {
        return 0;
    }

    protected Parameters getParameters() {
        return null;
    }

    public static class Context extends Parameters {
    }
}
