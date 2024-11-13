package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.RecursiveTask;

public class Creator  extends RecursiveTask<TreeMap<String, TreeSet<String>>> {
    private  TreeSet<String> set;
    private String url;
    public Creator (String url) throws IOException, InterruptedException {
        this.url = url;
        set = new TreeSet<>();
    }

    @Override
    protected TreeMap<String, TreeSet<String>> compute() {
        TreeMap <String, TreeSet<String>> map = Main.allMaps;
        try {
            set = new CallableSet(url).call();
        } catch (IOException | InterruptedException e) {
            System.out.println("404 error");
        }
        map.put(url, set);
        List<Creator> taskList = new ArrayList<>();
        for (String str : set) {
            try {
                Thread.sleep(100);
                Creator task = (Creator) new Creator(str).fork();
                taskList.add(task);
            } catch (IOException | InterruptedException e) {
                System.out.println("404 error");
            }
        }
        for (Creator task : taskList) {
            task.join();
            map.put(task.url, task.set);
        }
        return map;
    }
}
