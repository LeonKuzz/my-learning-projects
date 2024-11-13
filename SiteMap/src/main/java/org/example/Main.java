package org.example;

import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static TreeMap <String, TreeSet<String>> allMaps = new TreeMap<>();
    public static String mainURL = "https://lenta.ru/"; //https://sendel.ru/  https://skillbox.ru/ https://lenta.ru/)
    public static String fileAddress = "C:\\java_basics\\Multithreading\\SiteMap\\result\\SiteMap.txt";
    public static void main(String[] args) throws Exception {
        Long start = System.currentTimeMillis();

        allMaps = new ForkJoinPool().invoke(new Creator(mainURL));
        printSite(mainURL);
        Long finish = System.currentTimeMillis();
        System.out.println(finish - start + " мс" + System.lineSeparator() +
                (finish - start) / 60000 + " м");
        PrintWriter writer = new PrintWriter(fileAddress);
        writeString(mainURL, writer);
        writer.close();
   }
   public static void printSite (String url) {
        allMaps.get(url);
       System.out.println(url);
       for (String key : allMaps.get(url)) {
           if (allMaps.get(key) != null) {
               printSite(key);
           } else {
               System.out.println(key);
           }
       }
   }
   public static void writeString (String url, PrintWriter writer) {
       allMaps.get(url);
       writer.write(url + System.lineSeparator());
       for (String key : allMaps.get(url)) {
           if (allMaps.get(key) != null) {
               writeString(key, writer);
           } else {
               writer.write(key + System.lineSeparator());
           }
       }
   }
}