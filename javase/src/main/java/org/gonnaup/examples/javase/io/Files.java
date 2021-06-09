package org.gonnaup.examples.javase.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;

/**
 * 文件类示例
 *
 * @author gonnaup
 * @version 2021/6/8 11:19
 */
@Slf4j
public class Files {

    public File obtainFile() {
        URL systemResource = ClassLoader.getSystemResource("cfg.properties");
        if (systemResource == null) {
            throw new IllegalArgumentException("文件不存在");
        }
        File file = new File(systemResource.getPath());
        log.info("文件路径 {}", file.getAbsolutePath());
        log.info("文件 {} 文件夹", file.isDirectory() ? "是" : "不是");
        return file;
    }

    public static void main(String[] args) {
        Files files = new Files();
        files.obtainFile();
    }

}