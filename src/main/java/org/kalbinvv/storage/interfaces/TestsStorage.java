package org.kalbinvv.storage.interfaces;

import java.util.List;

import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;

public interface TestsStorage {
	public void addTest(Test test);
	public void removeTest(Test test);
	public List<Test> getTests();
	public void setAnswers(Test test, List<List<String>> answers);
	public void removeAnswers(Test test);
	public List<List<String>> getAnswers(Test test);
	public void addTestResult(TestResult testResult);
	public List<TestResult> getTestsResults();
}
