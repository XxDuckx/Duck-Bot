package com.duckbot.recorder;

import java.util.ArrayList;
import java.util.List;

public class ScriptRecorder {
    private final List<String> recordedLines = new ArrayList<>();
    private boolean recording = false;

    public void startRecording() {
        recordedLines.clear();
        recording = true;
    }

    public void stopRecording() {
        recording = false;
    }

    public boolean isRecording() {
        return recording;
    }

    public List<String> getRecordedLines() {
        return recordedLines;
    }

    public void recordTap(int x, int y) {
        recordedLines.add("TAP " + x + " " + y);
    }

    public void recordInput(String text) {
        recordedLines.add("INPUT " + text);
    }

    public void recordWait() {
        recordedLines.add("WAIT 1000"); // default wait time
    }
} 