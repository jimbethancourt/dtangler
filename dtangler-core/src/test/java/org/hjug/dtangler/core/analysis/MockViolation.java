//This product is provided under the terms of EPL (Eclipse Public License) 
//version 2.0.
//
//The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.core.analysis;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.hjug.dtangler.core.analysisresult.Violation;
import org.hjug.dtangler.core.dependencies.Dependable;

public class MockViolation implements Violation {

	private final String name;
	private final Set<Dependable> appliesTo = new HashSet<>();
	private final Severity severity;

	public MockViolation(String name) {
		this(name, Severity.warning, Collections.emptySet());
	}

	public MockViolation(String name, Severity severity) {
		this(name, severity, Collections.emptySet());
	}

	public MockViolation(String name, Severity severity,
			Set<Dependable> appliesTo) {
		this.name = name;
		this.severity = severity;
		this.appliesTo.addAll(appliesTo);
	}

	public String asText() {
		return name;
	}

	public Severity getSeverity() {
		return severity;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof MockViolation && this.name.equals(((MockViolation) obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public boolean appliesTo(Set<Dependable> dependables) {
		for (Dependable dependable : dependables) {
			if (appliesTo.contains(dependable))
				return true;
		}
		return false;
	}

	public Set<Dependable> getMembers() {
		return appliesTo;
	}
}
