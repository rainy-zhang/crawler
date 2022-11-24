package org.rainy.crawler;

import org.rainy.crawler.example.ExampleApplication;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class CrawlerApplication {

    public static void main(String[] args) throws IOException {
       new ExampleApplication().run("https://www.woyaogexing.com/shouji/");
    }


}
