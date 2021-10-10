package ge.devel.ocpp.ws.json16.api.impl;

import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.core.*;
import ge.devel.ocpp.ws.api.ServerEventHandler;
import ge.devel.ocpp.ws.api.decorators.SessionInformationDecorator;
import ge.devel.ocpp.ws.api.impl.AbstractRequestHandlerValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Component("Json16ServerCoreEventHandler")
public class Json16ServerCoreEventHandlerImpl extends AbstractRequestHandlerValidator implements ServerCoreEventHandler {

    @Value("${ge.devel.ocpp.interval.seconds.heartbeat}")
    private int heartbeatInterval;


    private final ServerEventHandler serverEventHandler;

    public Json16ServerCoreEventHandlerImpl(@Qualifier("Json16ServerEventHandler") final ServerEventHandler serverEventHandler) {
        this.serverEventHandler = serverEventHandler;
    }

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(final UUID sessionIndex, final AuthorizeRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("AuthorizeRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new AuthorizeConfirmation(new IdTagInfo(AuthorizationStatus.Accepted));
    }

    @Override
    public BootNotificationConfirmation handleBootNotificationRequest(final UUID sessionIndex, final BootNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("BootNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new BootNotificationConfirmation(ZonedDateTime.now(), this.heartbeatInterval, RegistrationStatus.Accepted);
    }

    @Override
    public DataTransferConfirmation handleDataTransferRequest(final UUID sessionIndex, final DataTransferRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("DataTransferRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new DataTransferConfirmation(DataTransferStatus.Accepted);
    }

    @Override
    public HeartbeatConfirmation handleHeartbeatRequest(final UUID sessionIndex, final HeartbeatRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("HeartbeatRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new HeartbeatConfirmation(ZonedDateTime.now());
    }

    @Override
    public MeterValuesConfirmation handleMeterValuesRequest(final UUID sessionIndex,final  MeterValuesRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("MeterValuesRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new MeterValuesConfirmation();
    }

    @Override
    public StartTransactionConfirmation handleStartTransactionRequest(final UUID sessionIndex, final StartTransactionRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("StartTransactionRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Accepted), new SecureRandom().nextInt());
    }

    @Override
    public StatusNotificationConfirmation handleStatusNotificationRequest(final UUID sessionIndex, final StatusNotificationRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("StatusNotificationRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new StatusNotificationConfirmation();
    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(final UUID sessionIndex, final StopTransactionRequest request) {
        SessionInformationDecorator info = this.serverEventHandler.getSessionInformationBySession(sessionIndex).orElseThrow(RuntimeException::new);
        log.info("StopTransactionRequest UUID = {} ChargePointIdentifier = {} request = {}", sessionIndex, info.getIdentity(), request);
        this.checkRequest(request);

        return new StopTransactionConfirmation();
    }
}
