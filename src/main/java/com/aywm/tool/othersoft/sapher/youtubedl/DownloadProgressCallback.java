package com.aywm.tool.othersoft.sapher.youtubedl;

public interface DownloadProgressCallback {
    void onProgressUpdate(float progress, long etaInSeconds);
}
