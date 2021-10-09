package ge.devel.ocpp.ws.api.impl;

import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRequestHandlerValidator {

    protected void checkRequest(Request request){
        log.debug("Validate Request[{}]", request.toString());
        if (!request.validate()){
            throw new IllegalArgumentException("Request is not VALID=>" + request.toString());
        }
    }
}
