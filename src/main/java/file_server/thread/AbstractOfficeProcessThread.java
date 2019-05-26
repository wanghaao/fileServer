package file_server.thread;


import org.apache.commons.logging.impl.SimpleLog;


public class AbstractOfficeProcessThread implements Runnable {

    private SimpleLog log = new SimpleLog("log");

    @Override
    public void run() {
        log.info("abstractOfficeProcessThread");
    }
}
