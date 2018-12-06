package com.meux.icarbonx.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.proto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestToolService {

    @Autowired
    private HttpClientService service;

    public boolean sendTo(ProtobuffFrame.Request request, String url) throws InvalidProtocolBufferException {
        ProtobuffFrame.Response response = service.sendPost(request, url);
        if(null != response && response.getCode()==0){
            return true;
        }
        return false;
    }


//    public boolean setRoleLevel(ProtobuffFrame.Request request,String url) throws InvalidProtocolBufferException {
//        ProtobuffFrame.Response response = service.sendPost(request, url);
//        if(null != response && response.getCode()==0){
//            return true;
//        }
//        return false;
//    }
//
//
//    public boolean sendPatchMail(ProtobuffFrame.Request request,String url) throws InvalidProtocolBufferException {
//        ProtobuffFrame.Response response = service.sendPost(request, url);
//        if(null != response && response.getCode()==0){
//            return true;
//        }
//        return false;
//    }

}
