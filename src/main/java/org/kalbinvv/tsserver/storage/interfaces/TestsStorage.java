package org.kalbinvv.tsserver.storage.interfaces;

import java.util.List;
import java.util.Map;

import org.kalbinvv.tscore.test.Answer;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;

public interface TestsStorage {
	public void addTest(Test test);
	public void removeTest(Test test);
	public List<Test> getTests();
	public void setAnswers(Test test, Map<String, Answer> answers);
	public void removeAnswers(Test test);
	public Map<String, Answer> getAnswers(Test test);
	public void addTestResult(TestResult testResult);
	public List<TestResult> getTestsResults();
}
