package org.hjug.dtangler.core.dependencyengine;

import org.hjug.dtangler.core.configuration.Arguments;
import org.hjug.dtangler.core.exception.DtException;

public abstract class AbstractDependencyEngine implements DependencyEngine {

	private String dependencyEngineId;

	public String getDependencyEngineId() {
		if (dependencyEngineId == null)
			return this.getClass().getSimpleName();
		return dependencyEngineId;
	}

	public void setDependencyEngineId(String dependencyEngineId) {
		this.dependencyEngineId = dependencyEngineId;
	}

	public abstract ArgumentsMatch getArgumentsMatchThisEngineExt(
			Arguments arguments);

	public ArgumentsMatch getArgumentsMatchThisEngine(Arguments arguments) {
		if (arguments == null) {
			throw new DtException("invalid arguments: null");
		}
		if (arguments.getDependencyEngineId() != null
				&& arguments.getDependencyEngineId().length() > 0) {
			if (dependencyEngineId != null
					&& arguments.getDependencyEngineId().equalsIgnoreCase(
							dependencyEngineId)) {
				return ArgumentsMatch.yes;
			} else {
				return ArgumentsMatch.no;
			}
		}
		return this.getArgumentsMatchThisEngineExt(arguments);
	}

}
