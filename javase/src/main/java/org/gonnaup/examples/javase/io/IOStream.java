package org.gonnaup.examples.javase.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author gonnaup
 * @version 2021/6/8 11:32
 */
@Slf4j
public class IOStream {

    public void inStream() {
        Files files = new Files();
        File inFile = files.obtainFile();
        try (InputStream in = new FileInputStream(inFile)) {
            byte[] inBuffer = new byte[256];
            int length = 0;
            StringBuilder builder = new StringBuilder();
            while ((length = in.read(inBuffer)) > 0) {
                builder.append(new String(inBuffer, 0, length));
            }
            log.info(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outStream() {
        String rootPath = ClassLoader.getSystemResource("").getPath();
        File outFile = new File(rootPath + File.separator + "out.tmp");
        try (OutputStream out = new FileOutputStream(outFile)) {
            String content = "你好！";
            out.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IOStream ioStream = new IOStream();
        ioStream.inStream();
        ioStream.outStream();
    }


}
