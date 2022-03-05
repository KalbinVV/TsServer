package org.kalbinvv.tsserver.events;

import java.util.List;

import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Question;
import org.kalbinvv.tscore.test.Test;
import org.kalbinvv.tscore.test.TestResult;
import org.kalbinvv.tscore.user.User;
import org.kalbinvv.tsserver.ServerStorage;
import org.kalbinvv.tsserver.TestingSystemServer;

public class OnCompleteTestEvent implements ServerEvent{

	@Override
	public Response handle(Request request, Connection connection) {
		User user = request.from();
		Test test = user.getTest();
		List<List<String>> correctAnswers = TestingSystemServer.getServerHandler()
				.getServerStorage().getAnswers(test.getID());
		int questionIndex = 0;
		int numberOfCorrectAnswers = 0;
		int numberOfAnswers = 0;
		for(Question question : test.getQuestions()) {
			for(String userSelect : question.getUserSelect()) {
				for(String correctAnswer : correctAnswers.get(questionIndex)) {
					if(userSelect.equals(correctAnswer)) {
						numberOfCorrectAnswers++;
					}
				}
			}
		}
		for(List<String> answers : correctAnswers) {
			numberOfAnswers += answers.size();
		}
		ServerStorage serverStorage = TestingSystemServer.getServerHandler().getServerStorage();
		TestResult testResult = new TestResult(numberOfAnswers, 
				numberOfCorrectAnswers, user, test);
		serverStorage.addTestResult(testResult);
		serverStorage.addLog(user, "Завершил тестирование");
		return new Response(ResponseType.Successful, testResult);
	}

}
