package org.xahla.core.helper;

import java.lang.management.ManagementFactory;

public class VGC_JVMHelper {

    public boolean isDebugMode() {
        var runtime = ManagementFactory.getRuntimeMXBean();
        var inputArgs = runtime.getInputArguments();

        return inputArgs.toString().contains("jdwp");
    }

}
