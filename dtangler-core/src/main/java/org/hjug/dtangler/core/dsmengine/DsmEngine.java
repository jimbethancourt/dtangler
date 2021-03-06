// This product is provided under the terms of EPL (Eclipse Public License) 
// version 2.0.
//
// The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.core.dsmengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hjug.dtangler.core.dependencies.Dependable;
import org.hjug.dtangler.core.dependencies.DependencyGraph;
import org.hjug.dtangler.core.dsm.Dsm;
import org.hjug.dtangler.core.dsm.DsmCell;
import org.hjug.dtangler.core.dsm.DsmRow;

public class DsmEngine {

	private final DependencyGraph dependencies;

	public DsmEngine(DependencyGraph dependencies) {
		this.dependencies = dependencies;
	}

	public Dsm createDsm() {
		List<Dependable> allItems = new ArrayList<>(dependencies.getAllItems());
		allItems.sort(new InstabilityComparator(dependencies));

		List<DsmRow> rows = new ArrayList<>(allItems.size());
		for (Dependable item : allItems)
			rows.add(new DsmRow(item, createRowCells(item, allItems)));
		return new Dsm(rows);
	}

	private List<DsmCell> createRowCells(Dependable rowItem,
			List<Dependable> allItems) {
		List<DsmCell> cells = new ArrayList<>();
		for (Dependable colItem : allItems)
			cells.add(createCell(rowItem, colItem));

		return cells;
	}

	private DsmCell createCell(Dependable rowItem, Dependable colItem) {
		int depCount = dependencies.getDependencyWeight(colItem, rowItem);
		return new DsmCell(colItem, rowItem, depCount);
	}
}
