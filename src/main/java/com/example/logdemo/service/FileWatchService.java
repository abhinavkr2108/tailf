package com.example.logdemo.service;

import java.io.RandomAccessFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileWatchService {
    private final static String FILE_NAME="log.txt";
    private final static String READ_MODE="r";
    private final static String DESTINATION="/topic/log";

    private final RandomAccessFile randomAccessFile;
    private long offset;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public FileWatchService() throws Exception {
        randomAccessFile = new RandomAccessFile(FILE_NAME, READ_MODE);
        offset = initialOffset();
    }

    @Scheduled(fixedDelay = 200, initialDelay = 3000)
    public void sendUpdates() throws Exception {
        long fileLength = randomAccessFile.length();
        randomAccessFile.seek(offset);

        while (randomAccessFile.getFilePointer() < fileLength) {
            String latestData = randomAccessFile.readLine();
            String payload = "{" + "\"content\":\"" + latestData + "\"" + "}";
            messagingTemplate.convertAndSend(DESTINATION, payload);
            
        }
        offset = fileLength;
    }
    private long initialOffset() throws Exception {
        long fileLength = randomAccessFile.length();
        if (fileLength == 0) {
            return 0;
        }

        long pos = fileLength - 1;
        int linesFound = 0;

        while (pos > 0 && linesFound < 10) {
            randomAccessFile.seek(pos);
            if (randomAccessFile.read() == '\n') {
                linesFound++;
            }
            pos--;
        }

        return pos;
    }
    

}
