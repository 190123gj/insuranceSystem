package com.born.insurance.integration.common;

import java.net.SocketTimeoutException;

public interface CallExternalInterface<T> {
	public T call() throws SocketTimeoutException;
}
