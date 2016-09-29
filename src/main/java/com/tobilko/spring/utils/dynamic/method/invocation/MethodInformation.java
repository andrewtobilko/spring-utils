package com.tobilko.spring.utils.dynamic.method.invocation;

import lombok.*;

/**
 * To gather all method invocation information in the single class.
 * A target to pass as a single parameter to the {@code MethodCaller#call}.
 *
 * Created by Andrew Tobilko on 9/30/2016.
 *
 */
@Data
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class MethodInformation {

    private @NonNull Object instance;
    private @NonNull String method;
    private Object[] arguments;

}
