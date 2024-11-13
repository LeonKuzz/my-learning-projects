package org.example;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

public class CallableSet implements Callable<TreeSet<String>> {
    private final String url;
    private TreeSet<String> newSet;

    public CallableSet(String url) {
        newSet = new TreeSet<>();
        this.url = url;
    }

    @Override
    public TreeSet<String> call() throws IOException, InterruptedException {
        return createMap(url);
    }

    public TreeSet<String> createMap(String startString) throws IOException, InterruptedException {
        Thread.sleep(100);
        Document page = null;
        String url = startString.strip();
        try {
            page = Jsoup.connect(url).get();

                Elements links = page.select("a[href]");
            HashSet<String> linksSet = getLinksSet(links, url);
            for (String str : linksSet) {
//                System.out.println(str);
                if (checking(str) ) { // &&!str.equals(url) && str.contains(url) && !str.contains("#")&& !str.matches(\"[file] [\"pdf]\")) {
//                    System.out.println(str);
                    str = lastSymbolIsSlash(str);
                    String added = searchNextSlash(url, str);
                    if (added.equals("file/") || added.contains(".") || added.contains("?")) {
                        continue;
                    }
//                    System.out.println(added);
                    String newUrl = startString.strip() + added;
                    String newString = "    " + startString + added;
                    if (newSet.contains(newString)) {
                        continue;
                    }
                    while  (!checking(newUrl)) {
                        added = searchNextSlash(newUrl, str);
                        newUrl = newUrl + added;
                        newString = newString + added;
                        if (added.equals("file/")  || added.contains(".") || added.contains("?")) {
                            break;
                        }
//                        Thread.sleep(5000);
                        if (newUrl.equals(str)) {
                            break;
                        }
                    }
                    if (newSet.contains(newString) || added.equals("file/")  || added.contains(".") || added.contains("?")) {
                        continue;
                    }

                    System.out.println(newString);
                    newSet.add(newString);
                }
            }
            return newSet;
        } catch (HttpStatusException e) {
            newSet = null;
            throw new HttpStatusException("404", 1, url);
        }
    }

    private boolean checking(String url) {
        boolean result = false;
        try {
            Document page = Jsoup.connect(url).get();
            result = true;
        } catch (IOException e) {
            System.out.println("404 error");
        }
        return result;
    }

    private static String searchNextSlash(String url, String str) {
        if (url.length() < str.length()) {
            int num = str.indexOf("/", url.length());
            String newUrl = str.substring(url.length(), num);
            newUrl = lastSymbolIsSlash(newUrl);
            return newUrl;
        } else {
            return url;
        }
    }

    private static String lastSymbolIsSlash(String str) {
        if (!str.substring(str.length() - 1).equals("/")) {
            str = str + "/";
        }
        return str;
    }
    private static HashSet<String> getLinksSet (Elements links, String url) {
        HashSet <String> linksSet = new HashSet<>();
        for (Element link : links) {
            String str = link.attr("abs:href");
            if (str.contains(url) && str.length() > url.length() && !str.contains("#")){
                linksSet.add(str);
            }
        }
        return linksSet;
    }
}