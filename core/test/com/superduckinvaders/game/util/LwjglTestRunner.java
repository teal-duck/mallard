package com.superduckinvaders.game.util;


import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.headless.HeadlessApplication;


/**
 * Runs the tests in a desktop application.
 */
public class LwjglTestRunner extends BlockJUnit4ClassRunner {
	/**
	 * Whether the test case had finished running.
	 */
	private AtomicBoolean finished = new AtomicBoolean(false);


	/**
	 * An ApplicationListener that runs the tests and sets finished to true.
	 */
	private class TestApplicationAdapter extends ApplicationAdapter {
		private RunNotifier notifier;


		TestApplicationAdapter(RunNotifier notifier) {
			this.notifier = notifier;
		}


		@Override
		public void create() {
			notifier.fireTestStarted(getDescription());
			// We should have no issues with exceptions because
			// ParentRunner should handle every one.
			runTests(notifier);
			notifier.fireTestFinished(getDescription());
			finished.set(true);
		}
	}


	/**
	 * Launch the test runner for a test class.
	 * 
	 * @param klass
	 *                The class to initialize with.
	 * @throws InitializationError
	 */
	public LwjglTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}


	/**
	 * Actually runs the test methods.
	 */
	private void runTests(RunNotifier notifier) {
		super.run(notifier);
	}


	/**
	 * Run the tests in a new LwjglApplication container.
	 * 
	 * @see BlockJUnit4ClassRunner#run
	 */
	@Override
	public void run(RunNotifier notifier) {
		// Create an ApplicationAdapter to run the test.
		HeadlessApplication app = new HeadlessApplication(new TestApplicationAdapter(notifier));

		try {
			// Wait for the test to terminate (or throw an exception).
			while (!finished.get()) {
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			// This might happen, but who cares?
		} finally {
			app.exit();
		}
	}
}
