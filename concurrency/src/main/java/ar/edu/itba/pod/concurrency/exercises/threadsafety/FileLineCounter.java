package ar.edu.itba.pod.concurrency.exercises.threadsafety;


import java.io.*;
import java.util.concurrent.Callable;

public class FileLineCounter implements Callable<Integer> {

    private final File file;

    public FileLineCounter(File file) {
        this.file = file;
    }

    public int countLines(File file) {
        if (file.isFile() && file.canRead()) {
            System.out.printf("F %s\n", file.getPath());
            try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath()))) {
                String line = reader.readLine();
                int i = 0;
                while (line != null) {
                    i += 1;
                    line = reader.readLine();
                }
                return i;
            } catch (IOException e) {
                return 0;
            }
        }
        System.out.printf("S %s\n", file.getPath());
        return 0;
    }

    @Override
    public Integer call() throws Exception {
        return countLines(file);
    }
}
