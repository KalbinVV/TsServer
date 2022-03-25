package org.kalbinvv.tsserver.events;

import java.util.List;

import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.interfaces.TestsStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Question;
import org.kalbinvv.tscore.test.QuestionType;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnCompleteTestEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		Test test = user.getTest();
		ServerStorage serverStorage = TestingSystemServer.getServerHandler()
				.getServerStorage();
		TestsStorage testsStorage = serverStorage.getTestsStorage();
		List<List<String>> correctAnswers = testsStorage.getAnswers(test);
		int questionIndex = 0;
		int numberOfCorrectAnswers = 0;
		int numberOfAnswers = 0;
		for(Question question : test.getQuestions()) {
			for(String userSelect : question.getUserSelect()) {
				for(String correctAnswer : correctAnswers.get(questionIndex)) {
					if(userSelect.equals(correctAnswer)) {
						numberOfCorrectAnswers++;
						break;
					}
				}
			}
			questionIndex++;
		}
		int answerIndex = 0;
		for(List<String> answers : correctAnswers) {
			if(test.getQuestions().get(answerIndex).getType() == QuestionType.TextFields) {
				numberOfAnswers++;
			}else {
				numberOfAnswers += answers.size();
			}
			answerIndex++;
		}
		TestResult testResult = new TestResult(numberOfAnswers, 
				numberOfCorrectAnswers, user, test);
		testsStorage.addTestResult(testResult);
		serverStorage.getLogsStorage().addLog(user, "Завершил тестирование");
		return new Response(ResponseType.Successful, testResult);
	}

}
