package com.srini.remote;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 503) {
            return new ServiceUnavailableException();
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
