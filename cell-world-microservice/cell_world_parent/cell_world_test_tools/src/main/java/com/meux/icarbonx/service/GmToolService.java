package com.meux.icarbonx.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.proto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GmToolService {

    private final HttpClientService service;

    @Autowired
    public GmToolService(HttpClientService service) {
        this.service = service;
    }

    public boolean sendTo(ProtobuffFrame.Request request, String url) throws InvalidProtocolBufferException {
        ProtobuffFrame.Response response = service.sendPost(request, url);
        return null != response && response.getCode() == 0;
    }


}
