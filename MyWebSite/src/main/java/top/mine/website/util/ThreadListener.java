package top.mine.website.util;

public interface ThreadListener {
    void afterDone();

    void afterStop();

    void afterInterrupt();
}