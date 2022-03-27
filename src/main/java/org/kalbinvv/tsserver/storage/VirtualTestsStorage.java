package org.kalbinvv.tsserver.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kalbinvv.tsserver.storage.interfaces.TestsStorage;
import org.kalbinvv.tscore.test.Answer;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;

public class VirtualTestsStorage implements TestsStorage{
	
	private final List<Test> tests;
	private final List<TestResult> results;
	private final Map<String, Map<String, Answer>> answers;

	public VirtualTestsStorage() {
		tests = new ArrayList<Test>();
		results = new ArrayList<TestResult>();
		answers = new HashMap<String, Map<String, Answer>>();
	}
	
	@Override
	public List<Test> getTests() {
		return tests;
	}

	@Override
	public void addTest(Test test) {
		tests.add(test);
	}
	
	@Override
	public void removeTest(Test test) {
		tests.removeIf((Test tst) -> {
			return tst.getName().equals(test.getName());
		});
	}

	@Override
	public void setAnswers(Test test, Map<String, Answer> answers) {
		this.answers.put(test.getName(), answers);
	}

	@Override
	public void removeAnswers(Test test) {
		answers.keySet().removeIf((String testName) -> {
			return testName.equals(test.getName());
		});
	}
	
	@Override
	public Map<String, Answer> getAnswers(Test test) {
		for(Test tst : tests) {
			if(tst.getName().equals(test.getName())) {
				return answers.get(tst.getName());
			}
		}
		return null;
	}

	@Override
	public void addTestResult(TestResult testResult) {
		results.add(testResult);
	}

	@Override
	public List<TestResult> getTestsResults() {
		return results;
	}
	
}
