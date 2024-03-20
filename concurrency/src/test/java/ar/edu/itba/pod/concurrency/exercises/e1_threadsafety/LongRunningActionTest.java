package ar.edu.itba.pod.concurrency.exercises.e1_threadsafety;

import ar.edu.itba.pod.concurrency.exercises.e1.threadSafety.locks.LongRunningAction;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongRunningActionTest {

	@Test
	public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
		// given
		ExecutorService executorService = Executors.newCachedThreadPool();
		Phaser ph = new Phaser(1);
		assertEquals(0, ph.getPhase());

		// when
		executorService.submit(new LongRunningAction("thread-1", ph));
		executorService.submit(new LongRunningAction("thread-2", ph));
		executorService.submit(new LongRunningAction("thread-3", ph));

		// then
		ph.arriveAndAwaitAdvance();
		assertEquals(1, ph.getPhase());

		// and
		executorService.submit(new LongRunningAction("thread-4", ph));
		executorService.submit(new LongRunningAction("thread-5", ph));
		ph.arriveAndAwaitAdvance();
		assertEquals(2, ph.getPhase());

		ph.arriveAndDeregister();
	}
}
