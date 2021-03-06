// This product is provided under the terms of EPL (Eclipse Public License) 
// version 2.0.
//
// The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.core.testutil.ruleanalysis;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.hjug.dtangler.core.configuration.Group;
import org.hjug.dtangler.core.ruleanalysis.GroupRuleMember;
import org.hjug.dtangler.core.ruleanalysis.Rule;
import org.hjug.dtangler.core.ruleanalysis.RuleMember;
import org.hjug.dtangler.core.ruleanalysis.SingleRuleMember;

public class MockRule extends Rule {

	public MockRule() {
		super(Rule.Type.cannotDepend, new SingleRuleMember("TestRuleMember"),
				new HashSet<>(Collections.singletonList(new SingleRuleMember(
						"TestRuleMember2"))));
	}

	public MockRule(Type type, RuleMember leftSide, Set<RuleMember> rightSide) {
		super(type, leftSide, rightSide);
	}

	public MockRule(Type type, Group leftSide, Group rightside) {
		super(type, new GroupRuleMember(leftSide), new HashSet<>(Collections.singletonList(new GroupRuleMember(rightside))));
	}

	public MockRule(Type type, Group leftSide, String rightSide) {
		super(type, new GroupRuleMember(leftSide), new HashSet<>(Collections.singletonList(new SingleRuleMember(rightSide))));
	}

	public MockRule(Type type, String leftSide, Group rightSide) {
		super(type, new SingleRuleMember(leftSide), new HashSet<>(Collections.singletonList(new GroupRuleMember(rightSide))));
	}
}