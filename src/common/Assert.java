package common;

public final class Assert {

	public static void verify(boolean condition) {
		if (condition)
			return;
		throw new AssertionFailed();
	}

	public static void verify(boolean condition, String message) {
		if (condition)
			return;
		throw new AssertionFailed(message);
	}

}
