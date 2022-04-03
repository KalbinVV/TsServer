package org.kalbinvv.tsserver.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kalbinvv.tsserver.storage.ServerStorage;
import org.kalbinvv.tsserver.storage.interfaces.TestsStorage;
import org.kalbinvv.tscore.net.Connection;
import org.kalbinvv.tscore.net.Request;
import org.kalbinvv.tscore.net.Response;
import org.kalbinvv.tscore.net.ResponseType;
import org.kalbinvv.tscore.test.Answer;
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
		Map<String, Answer> testsAnswers = testsStorage.getAnswers(test);
		int correctAnswersAmount = 0;
		int allAnswersAmount = 0;
		Map<String, Integer> answersResult = new HashMap<String, Integer>();
		for(Question question : test.getQuestions()) {
			if(question.getType() == QuestionType.TextFields) {
				List<String> answers = testsAnswers.get(question.getTitle()).getVariants();
				answersResult.put(question.getTitle(), 0);
				for(String variant : question.getUserSelect()) {
					if(answers.contains(variant)) {
						correctAnswersAmount++;
						answersResult.put(question.getTitle(), 1);
						break;
					}
				}
				allAnswersAmount++;
			}else if(question.getType() == QuestionType.CheckBoxes) {
				List<String> answers = testsAnswers.get(question.getTitle()).getVariants();
				allAnswersAmount += answers.size();
				answersResult.put(question.getTitle(), 0);
				for(String variant : question.getUserSelect()) {
					if(answers.contains(variant)) {
						correctAnswersAmount++;
						answersResult.put(question.getTitle(), 
								answersResult.get(question.getTitle()) + 1);
					}
				}
			}
		}
		TestResult testResult = new TestResult(allAnswersAmount, correctAnswersAmount,
				user, test, answersResult);
		testsStorage.addTestResult(testResult);
		serverStorage.getLogsStorage().addLog(user, "Завершил тестирование '" 
				+ test.getName() + "'");
		return new Response(ResponseType.Successful, testResult);
	}

}
