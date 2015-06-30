/*******************************************************************************
 * Copyright (c) 2010-2015, Benedek Izso, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/

package hu.bme.mit.trainbenchmark.sql.process;

import java.io.IOException;

public class MySQLProcess {

	public static void startSQLProcess() throws IOException {
		stopSQLProcess();

		final Runtime rt = Runtime.getRuntime();
		final String[] command = { "sudo", "service", "mysql", "start" };
		try {
			final Process pr = rt.exec(command);
			pr.waitFor();
			if (pr.exitValue() != 0) {
				throw new Exception("Failed to start MySQL process");
			}
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

	public static void stopSQLProcess() throws IOException {
		final Runtime rt = Runtime.getRuntime();
		final String[] command = { "sudo", "service", "mysql", "stop" };
		try {
			final Process pr = rt.exec(command);
			pr.waitFor();
//			if (pr.exitValue() != 0) {
//				throw new Exception("Failed to stop MySQL process");
//			}
		} catch (final Exception e) {
			throw new IOException(e);
		}
	}

}
