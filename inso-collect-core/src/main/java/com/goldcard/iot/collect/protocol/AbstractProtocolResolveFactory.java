package com.goldcard.iot.collect.protocol;


import com.goldcard.iot.collect.util.SpringContextHolder;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class AbstractProtocolResolveFactory {

    public static AbstractProtocolResolve create(String protocolNo) {
		List<AbstractProtocolResolve> protocols = SpringContextHolder.getBeansWithType(AbstractProtocolResolve.class);
		if (CollectionUtils.isNotEmpty(protocols)) {
			Optional<AbstractProtocolResolve> optional = protocols.stream().filter(p -> p.protocolNo().equals(protocolNo))
					.findFirst();
			if (optional.isPresent()) {
				return optional.get();
			}
		}
        return null;
    }
}
