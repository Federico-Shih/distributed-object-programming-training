package ar.edu.itba.pod.concurrency.iii.threadcompletable;


import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class FileLineCounter implements Runnable {

    private final File file;
    private final CompletableFuture<Integer> linesFuture;
    private final CompletableFuture<Long> sizeFuture;

    public FileLineCounter(File file, CompletableFuture<Integer> linesFuture, CompletableFuture<Long> sizeFuture) {
        this.file = file;
        this.linesFuture = linesFuture;
        this.sizeFuture = sizeFuture;
    }

    @Override
    public void run() {
        if (file.isFile() && file.canRead()) {
            try (Stream<String> values = Files.lines(file.toPath())){
                linesFuture.complete(values.toList().size());
                sizeFuture.complete(Files.size(file.toPath()));
            } catch (IOException e) {
                linesFuture.complete(0);
                sizeFuture.complete(0L);
            }

        } else {
            linesFuture.complete(0);
            sizeFuture.complete(0L);
        }
    }
}
