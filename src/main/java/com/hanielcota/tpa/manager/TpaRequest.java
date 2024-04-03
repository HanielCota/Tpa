package com.hanielcota.tpa.manager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TpaRequest {

    private final String sender;
    private final long timestamp = System.currentTimeMillis();

}
