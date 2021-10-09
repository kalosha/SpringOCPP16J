package ge.devel.ocpp.ws.json16.api.decorators;

import eu.chargetime.ocpp.model.SessionInformation;

import java.util.StringJoiner;

public interface OcppToStringDecorator {

    static String toString(SessionInformation value) {
        return new StringJoiner(", ", "{", "}")
                .add(new StringJoiner("=").add("Identifier").add(value.getIdentifier()).toString())
                .add(new StringJoiner("=").add("Address").add(value.getAddress().toString()).toString())
                .add(new StringJoiner("=").add("SOAPtoURL").add(value.getSOAPtoURL()).toString())
                .toString();
    }

}
