package hu.bme.mit.trainbenchmark.benchmark.iqdcore.config;

public enum Checker {
	LOCAL(""), //
	REMOTEONLY("RemoteOnly");

	private String name;

	Checker(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}