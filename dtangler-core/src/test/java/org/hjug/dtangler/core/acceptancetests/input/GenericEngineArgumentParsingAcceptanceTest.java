// This product is provided under the terms of EPL (Eclipse Public License) 
// version 2.0.
//
// The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.core.acceptancetests.input;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.hjug.dtangler.core.configuration.Arguments;
import org.hjug.dtangler.core.configuration.ParserConstants;
import org.hjug.dtangler.core.dependencies.Dependable;
import org.hjug.dtangler.core.dependencies.Dependencies;
import org.hjug.dtangler.core.input.ArgumentBuilder;
import org.hjug.dtangler.core.input.CommandLineParser;
import org.hjug.dtangler.core.testutil.ClassPathEntryFinder;
import org.hjug.dtangler.genericengine.dependencyengine.GenericDependencyEngine;
import org.hjug.dtangler.genericengine.types.ItemScope;
import org.junit.Test;

public class GenericEngineArgumentParsingAcceptanceTest {

	/*!!
	 #{set_header 'Run options: Generic engine -specific'}

	 With generic engine, you can read dependency definitions from
	 a text file or standard input.

	 Dependency definitions file syntax:
	 >>>>
	 dependencies         : (dependencyDefinition | itemDefinition)*
	 dependencyDefinition : dependant + ':' dependee + '\\n'
	 itemDefinition       : dependable
	 dependant            : dependable
	 dependee             : dependable
	 dependaple           : displayname | scope '{' fullyqualifiedname '}'
	 fullyqualifiedname   : parentfqn? displayname
	 parentfqn            : fullyqualifiedname
	 displayname          : string
	 <<<<
	 Strings must be URL encoded (for example space must be encoded in format %20).

	 Scopes define the level of detail for the dependency analysis.
	 
	 Simple example - we all know what Homer depends on:
	 >>>>
	 Homer : Beer
	 Homer : Pizza
	 Pizza : Cheese Pepperoni
	 <<<<

	 This has exactly the same meaning:
	 >>>>
	 {Homer} : {Beer}
	 {Homer} : {Pizza}
	 {Pizza} : {Cheese}
	 {Pizza} : {Pepperoni}
	 <<<<
	 
	 Java jars, packages and classes using fully qualified names:
	 >>>>
	 location{foo.jar}
	 package{foo.jar foo.jar/eg.process}
	 package{foo.jar foo.jar/eg.filters}
	 class{foo.jar foo.jar/eg.filters foo.jar/eg.filters.InFilter} : class{foo.jar foo.jar/eg.process foo.jar/eg.process.Process}
	 <<<<
	 
	 That's awfully verbose - it can be shortened like this:
	 >>>>
	 location{foo.jar}
	 package{foo.jar eg.process}
	 package{foo.jar eg.filters}
	 class{foo.jar eg.filters InFilter} : class{foo.jar eg.process Process}
	 <<<<
	 
	 */

	private String scopeKey = CommandLineParser
			.getKeyString(ParserConstants.SCOPE_KEY);
	private String inputKey = CommandLineParser
			.getKeyString(ParserConstants.INPUT_KEY);
	private String engineKey = CommandLineParser
			.getKeyString(ParserConstants.DEPENDENCY_ENGINE_ID_KEY);

	@Test
	public void defaultScope() {

		/*!
		 If the =#{genericEngineScope}= option is omitted, the first scope
		 in the dependency definitions file is used.
		 
		 For example reading the dependency definitions from a file:
		 >>>>
		 #{jarDefaultScopeArguments}
		 <<<<

		 Reading from standard input:
		 >>>>
		 #{jarDefaultScopeArgumentsStandardInput}
		 <<<<

		 The default scope is 0 in the dependency definitions example below:
		 >>>>
		 #{dtFileContents}
		 <<<<
		 */
		String corePath = ClassPathEntryFinder.getPathContaining("core");
		String dtPath = corePath
				+ "/org/hjug/dtangler/core/acceptancetests/input/dependencies.dt";
		
		Arguments args = new Arguments();
		args.setInput(Collections.singletonList(dtPath));
		GenericDependencyEngine engine = new GenericDependencyEngine();
		Dependencies dependencies = engine.getDependencies(args);
		Set<Dependable> scope0Items = dependencies
				.getDependencyGraph(new ItemScope("0", 0))
				.getAllItems();
		assertEquals("0", dependencies.getDefaultScope().getDisplayName());
		assertEquals(1, scope0Items.size());
	}

	private String loadFile(File file) throws IOException {
		String fileContent = "";
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] data = new byte[fileInputStream.available()];
			fileInputStream.read(data, 0, data.length);
			fileContent = new String(data);
		} finally {
			if (fileInputStream != null)
				fileInputStream.close();
		}
		return fileContent;
	}

	@Test
	public void definedScope() {
		/*!
		 You can produce a DSM with selected scope by using the =#{scopeKey}= run option.
		  
		 For example: 
		 >>>>
		 #{jarDefinedScopeArguments}
		 <<<<
		 */

		String corePath = ClassPathEntryFinder.getPathContaining("core");
		String dtPath = corePath
				+ "/org/hjug/dtangler/genericengine/dependencyengine/testdata/testParsing2.dt";

		String scope = scopeKey + "2";

		Arguments args = new ArgumentBuilder().build(new String[] { scope,
				inputKey + dtPath });

		GenericDependencyEngine engine = new GenericDependencyEngine();
		Dependencies dependencies = engine.getDependencies(args);
		Set<Dependable> scope2Items = dependencies
				.getDependencyGraph(new ItemScope("2", 2))
				.getAllItems();
		assertEquals("2", dependencies.getDefaultScope().getDisplayName());
		assertEquals(5, scope2Items.size());

	}

}
