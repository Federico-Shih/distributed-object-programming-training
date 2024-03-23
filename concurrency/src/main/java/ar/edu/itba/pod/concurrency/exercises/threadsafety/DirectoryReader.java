package ar.edu.itba.pod.concurrency.exercises.threadsafety;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class DirectoryReader {
    private final List<File> files;
    private boolean isFinished = false;

    public DirectoryReader() {
        files = new LinkedList<>();
    }

    public boolean hasFiles() {
        return !files.isEmpty();
    }

    public List<File> getFiles() {
        return this.files;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void readDirectory(String uri) {
        try (Stream<Path> paths = Files.walk(Paths.get(uri), 1)) {
            paths.forEach((path) -> {
                files.add(path.toFile());
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            isFinished = true;
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        DirectoryReader reader = new DirectoryReader();
        reader.readDirectory("./src");


        List<Callable<Integer>> readFiles = reader.getFiles().stream().map(FileLineCounter::new).collect(Collectors.toList());
        try {
            List<Future<Integer>> finishedFiles = service.invokeAll(readFiles);
            service.shutdown();
            service.awaitTermination(30, TimeUnit.SECONDS);
            service.shutdownNow();
            int resultLines = finishedFiles.stream().map((future) -> {
                if (future.isDone()) {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return 0;
                    }
                }
                return 0;
            }).reduce(Integer::sum).orElse(0);
            System.out.printf("Total lines: %d\n", resultLines);
        } catch (InterruptedException e) {

        }
    }
}
