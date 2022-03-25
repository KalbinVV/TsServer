package org.kalbinvv.tsserver.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kalbinvv.tsserver.storage.interfaces.TestsStorage;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;

public class VirtualTestsStorage implements TestsStorage{
	
	private final List<Test> tests;
	private final List<TestResult> testsResults;
	private final Map<String, List<List<String>>> testsAnswers;

	public VirtualTestsStorage() {
		tests = new ArrayList<Test>();
		testsResults = new ArrayList<TestResult>();
		testsAnswers = new HashMap<String, List<List<String>>>();
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
	public void setAnswers(Test test, List<List<String>> answers) {
		testsAnswers.put(test.getName(), answers);
	}

	@Override
	public void removeAnswers(Test test) {
		testsAnswers.keySet().removeIf((String testName) -> {
			return testName.equals(test.getName());
		});
	}
	
	@Override
	public List<List<String>> getAnswers(Test test) {
		for(Test tst : tests) {
			if(tst.getName().equals(test.getName())) {
				return testsAnswers.get(tst.getName());
			}
		}
		return null;
	}

	@Override
	public void addTestResult(TestResult testResult) {
		testsResults.add(testResult);
	}

	@Override
	public List<TestResult> getTestsResults() {
		return testsResults;
	}
	
}
