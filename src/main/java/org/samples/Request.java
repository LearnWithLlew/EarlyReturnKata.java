package org.samples;

import jakarta.servlet.http.HttpServletResponse;
import org.samples.required.ActionCode;
import org.samples.required.ClientAbortException;
import org.samples.required.Parameters;
import org.samples.required.RequestBase;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;


public class Request extends RequestBase {

  public void doParseParameters() {
    if (parametersParsed) {
      return;
    }
    parametersParsed = true;


    Parameters parameters = coyoteRequest.getParameters();

    // Set this every time in case limit has been changed via JMX
    int maxParameterCount = getMaxParameterCount();
    if (parts != null && maxParameterCount > 0) {
      maxParameterCount -= parts.size();
    }
    parameters.setLimit(maxParameterCount);

    // getCharacterEncoding() may have been overridden to search for
    // hidden form field containing request encoding
    Charset charset = getCharset();

    boolean useBodyEncodingForURI = connector.getUseBodyEncodingForURI();
    parameters.setCharset(charset);
    if (useBodyEncodingForURI) {
      parameters.setQueryStringCharset(charset);
    }
    // Note: If !useBodyEncodingForURI, the query string encoding is
    // that set towards the start of CoyoteAdapter.service()

    parameters.handleQueryParameters();

    if (usingInputStream || usingReader) {
      return;
    }

    String contentType = getContentType();
    if (contentType == null) {
      contentType = "";
    }
    int semicolon = contentType.indexOf(';');
    if (semicolon >= 0) {
      contentType = contentType.substring(0, semicolon).trim();
    } else {
      contentType = contentType.trim();
    }

    if ("multipart/form-data".equals(contentType)) {
      parseParts();
      if (partsParseException instanceof IllegalStateException) {
        parametersParseException = (IllegalStateException) partsParseException;
      } else if (partsParseException != null) {
        parametersParseException = new InvalidParameterException(partsParseException.getMessage());
      }
      return;
    }

    if (!getConnector().isParseBodyMethod(getMethod())) {
      return;
    }

    if (!("application/x-www-form-urlencoded".equals(contentType))) {
      return;
    }

    int len = getContentLength();

    if (len > 0) {
      int maxPostSize = connector.getMaxPostSize();
      if ((maxPostSize >= 0) && (len > maxPostSize)) {
        String message = sm.getString("coyoteRequest.postTooLarge");
        Context context = getContext();
        if (context != null && context.getLogger().isDebugEnabled()) {
          context.getLogger().debug(message);
        }
        checkSwallowInput();
        parametersParseException =
                new ClientAbortException(message, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
        return;
      }
      byte[] formData = null;
      if (len < CACHED_POST_LEN) {
        if (postData == null) {
          postData = new byte[CACHED_POST_LEN];
        }
        formData = postData;
      } else {
        formData = new byte[len];
      }
      try {
        readPostBodyFully(formData, len);
      } catch (IOException e) {
        // Client disconnect
        Context context = getContext();
        if (context != null && context.getLogger().isDebugEnabled()) {
          context.getLogger().debug(sm.getString("coyoteRequest.parseParameters"), e);
        }
        response.getCoyoteResponse().action(ActionCode.CLOSE_NOW, null);
        if (e instanceof ClientAbortException) {
          parametersParseException = new InvalidParameterException(e.getMessage());
        } else {
          parametersParseException = new ClientAbortException(e);
        }
        return;
      }
      parameters.processParameters(formData, 0, len);
    } else if ("chunked".equalsIgnoreCase(coyoteRequest.getHeader("transfer-encoding"))) {
      byte[] formData = null;
      try {
        formData = readChunkedPostBody();
      } catch (IllegalStateException ise) {
        parametersParseException = ise;
        return;
      } catch (IOException e) {
        // Client disconnect
        Context context = getContext();
        if (context != null && context.getLogger().isDebugEnabled()) {
          context.getLogger().debug(sm.getString("coyoteRequest.parseParameters"), e);
        }
        response.getCoyoteResponse().action(ActionCode.CLOSE_NOW, null);
        if (e instanceof ClientAbortException) {
          parametersParseException = new InvalidParameterException();
        } else {
          parametersParseException = new InvalidParameterException();
        }
        return;
      }
      if (formData != null) {
        parameters.processParameters(formData, 0, formData.length);
      }
    }
  }


}