// LoggingService.java
package com.duckbot.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoggingService {
    private static final LoggingService INSTANCE = new LoggingService();
    private final List<LogListener> listeners = new CopyOnWriteArrayList<>();

    private LoggingService() {}
    public static LoggingService getInstance() { return INSTANCE; }

    public void log(String message) {
        String ts = String.format("[%1$tF %1$tT] %2$s", System.currentTimeMillis(), message);
        listeners.forEach(l -> l.onLog(ts));
    }

    public void registerListener(LogListener l) { listeners.add(l); }
    public void unregisterListener(LogListener l) { listeners.remove(l); }
}