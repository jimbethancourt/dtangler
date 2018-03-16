// This product is provided under the terms of EPL (Eclipse Public License) 
// version 2.0.
//
// The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.core.textui;

import org.hjug.dtangler.core.analysisresult.AnalysisResult;
import org.hjug.dtangler.core.analysisresult.Violation.Severity;
import org.hjug.dtangler.core.dsm.Dsm;
import org.hjug.dtangler.core.dsm.DsmCell;
import org.hjug.dtangler.core.dsm.DsmRow;

public class DSMWriter {

	private final Writer writer;

	public DSMWriter(Writer writer) {
		this.writer = writer;

	}

	public void printDsm(Dsm dsm, AnalysisResult analysisResult) {
		printColumnHeaders(dsm.getRows().size());
		int i = 1;
		for (DsmRow row : dsm.getRows()) {
			printPackage(i++, row, analysisResult);
		}

	}

	private void printColumnHeaders(int size) {
		printEmptyRowHeader();
		for (int i = 1; i <= size; i++)
			printCell(Integer.toString(i));
		nextRow();
	}

	private void printPackage(int index, DsmRow row,
			AnalysisResult analysisResult) {
		printRowHeader(index, row.getDependee().getDisplayName(), row
				.getDependee().getContentCount());
		for (DsmCell dep : row.getCells())
			printCell(formatDependency(dep, analysisResult));
		nextRow();
	}

	private String formatDependency(DsmCell dep, AnalysisResult analysisResult) {
		if (!dep.isValid())
			return "####";
		if (dep.getDependencyWeight() == 0)
			return "";
		String s = Integer.toString(dep.getDependencyWeight());
		// FIXME: Severity.error might mean something else than a cycle, we
		// should change the UI and show an 'E' instead
		if (!analysisResult.getViolations(dep.getDependency(), Severity.error)
				.isEmpty())
			s = s + "C";
		return s;
	}

	private void printEmptyRowHeader() {
		print(String.format("%51s", ""));
	}

	private void printRowHeader(int rowId, String name, int pkgCount) {
		print(String.format("%3s %40s (%3s) ", rowId,
				formatName(name), pkgCount));
	}

	private String formatName(String name) {
		if (name.length() <= 40)
			return name;
		return ".." + name.substring(name.length() - 38);
	}

	private void printCell(String content) {
		print("|" + String.format("%4s", content));
	}

	private void nextRow() {
		println();
	}

	private void print(String s) {
		writer.print(s);
	}

	private void println() {
		writer.println("|");
	}

}