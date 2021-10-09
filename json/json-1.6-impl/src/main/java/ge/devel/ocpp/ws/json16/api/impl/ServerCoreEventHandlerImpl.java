package ge.devel.ocpp.ws.json16.api.impl;

import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.core.*;
import ge.devel.ocpp.ws.json16.api.ServerEventHandler;
import ge.devel.ocpp.ws.json16.api.decorators.SessionInformationDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Component("ServerCoreEventHandler")
public class ServerCoreEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerCoreEventHandler {

    @Value("${ge.devel.ocpp.interval.seconds.heartbeat}")
    private int heartbeatInterval;

    private ServerEventHandler serverEventHandler;

    public ServerCoreEventHandlerImpl(ServerEventHandler serverEventHandler) {
        this.serverEventHandler = serverEventHandler;
    }

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(UUID sessionIndex, AuthorizeRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("AuthorizeRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new AuthorizeConfirmation(new IdTagInfo(AuthorizationStatus.Accepted));
    }

    @Override
    public BootNotificationConfirmation handleBootNotificationRequest(UUID sessionIndex, BootNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("BootNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new BootNotificationConfirmation(ZonedDateTime.now(), this.heartbeatInterval, RegistrationStatus.Accepted);
    }

    @Override
    public DataTransferConfirmation handleDataTransferRequest(UUID sessionIndex, DataTransferRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("DataTransferRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new DataTransferConfirmation(DataTransferStatus.Accepted);
    }

    @Override
    public HeartbeatConfirmation handleHeartbeatRequest(UUID sessionIndex, HeartbeatRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("HeartbeatRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new HeartbeatConfirmation(ZonedDateTime.now());
    }

    @Override
    public MeterValuesConfirmation handleMeterValuesRequest(UUID sessionIndex, MeterValuesRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("MeterValuesRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new MeterValuesConfirmation();
    }

    @Override
    public StartTransactionConfirmation handleStartTransactionRequest(UUID sessionIndex, StartTransactionRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("StartTransactionRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);


        return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Accepted), new SecureRandom().nextInt());
    }

    @Override
    public StatusNotificationConfirmation handleStatusNotificationRequest(UUID sessionIndex, StatusNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("StatusNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new StatusNotificationConfirmation();
    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(UUID sessionIndex, StopTransactionRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow();
        log.info("StopTransactionRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);

        return new StopTransactionConfirmation();
    }
}
