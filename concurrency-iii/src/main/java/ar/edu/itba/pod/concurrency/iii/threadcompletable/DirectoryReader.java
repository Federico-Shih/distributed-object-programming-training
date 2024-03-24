package ar.edu.itba.pod.concurrency.iii.threadcompletable;

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
        ExecutorService service;
        try {
            service = Executors.newCachedThreadPool();
            DirectoryReader reader = new DirectoryReader();
            reader.readDirectory("/dev");

            List<CompletableFuture<String>> resultsFuture = new LinkedList<>();
            reader.getFiles().forEach((file) -> {
                CompletableFuture<Integer> lineCount = new CompletableFuture<>();
                CompletableFuture<Long> sizeCount = new CompletableFuture<>();

                FileLineCounter counter = new FileLineCounter(file, lineCount, sizeCount);
                service.submit(counter);
                resultsFuture.add(lineCount.thenCombine(sizeCount, (lc, sc) -> String.format("%s LINEAS %d TAMANO %d\n", file.getPath(), lc, sc)));
            });

            try {
                service.shutdown();
                service.awaitTermination(30, TimeUnit.SECONDS);
                StringBuilder builder = new StringBuilder();
                for (CompletableFuture<String> cf : resultsFuture) {
                    builder.append(cf.join());
                }
                System.out.println(builder);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            service.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
