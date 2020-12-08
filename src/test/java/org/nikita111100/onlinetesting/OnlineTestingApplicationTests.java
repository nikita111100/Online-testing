package org.nikita111100.onlinetesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.nikita111100.onlinetesting.model.persistent.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OnlineTestingApplicationTests {
	@Autowired
	private EntityManager em;
	@Test
	void contextLoads() {
		Assertions.assertEquals(1,1);
	}
	@Test
	public void FindQuestionsBytestId(){
		Query query = (Query) em.createNamedQuery("getAllQuestionsByTest");
		query.setParameter(1,1);
		List<Question> a = query.getResultList();
		assertNotNull(a);
		assertFalse(a.isEmpty());
		System.out.println("тут идет вывод");
		for (int i = 0; i < a.size(); i++) {
			System.out.println(a.get(i));
		}
	}

}
