package com.goldcard.iot.collect.protocol;


import com.goldcard.iot.collect.util.SpringContextHolder;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class AbstractProtocolHandlerFactory {

    public static AbstractProtocolHandler create(String protocolNo) {
		List<AbstractProtocolHandler> protocols = SpringContextHolder.getBeansWithType(AbstractProtocolHandler.class);
		if (CollectionUtils.isNotEmpty(protocols)) {
			Optional<AbstractProtocolHandler> optional = protocols.stream().filter(p -> p.protocolNo().equals(protocolNo))
					.findFirst();
			if (optional.isPresent()) {
				return optional.get();
			}
		}
        return null;
    }
}
