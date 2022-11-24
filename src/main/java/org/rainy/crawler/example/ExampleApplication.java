package org.rainy.crawler.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class ExampleApplication {
    
    public void run(String url) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Elements nodeElements = document.select(".pMain");

        for (Element nodeElement : nodeElements) {
            Elements divs = nodeElement.select(".txList-sj ");
            for (Element div : divs) {
                Element a_element = div.selectFirst(".img");
                String a_href = a_element.attr("abs:href");
                Document document1 = Jsoup.connect(a_href).get();
                Element img = document1.selectFirst(".lazy");
                String src = img.absUrl("src");
                downloadImage(src);
            }
        }

        Element pageElement = document.selectFirst(".page");
        Element lastPageElement = pageElement.select("a").last();
        String nextPageUrl = lastPageElement.attr("abs:href");

        if (nextPageUrl.contains("_20")) {
            return;
        }

        run(nextPageUrl);
    }
    
    private void downloadImage(String src) throws IOException {
        String fileName = src.substring(src.lastIndexOf("/"));
        URL url = new URL(src);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        System.out.println("downloading -> " + src);
        File targetFile = new File("D://temp");
        if (!targetFile.exists()) {
            targetFile.mkdir();
        }
        FileOutputStream fos = new FileOutputStream(targetFile + fileName);
        byte[] buffer = new byte[8192];
        while (inputStream.read(buffer) != -1) {
            fos.write(buffer);
        }
    }
    
}
