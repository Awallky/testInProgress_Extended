package org.jenkinsci.plugins.testinprogress.events;

public interface ITestEventListener {

	public void event(IRunTestEvent testEvent);

}