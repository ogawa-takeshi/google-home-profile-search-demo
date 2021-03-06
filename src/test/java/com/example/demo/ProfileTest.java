package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileTest {

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void mapping() {
		Profile profile = entityManager.persistAndFlush(new Profile(null, "小川", LocalDate.of(1981, 1, 8), BloodType.A, "元気です"));
		Assertions.assertThat(profile.getId()).isNotNull();
		Assertions.assertThat(profile.getName()).isEqualTo("小川");
		Assertions.assertThat(profile.getBirthday()).isEqualTo(LocalDate.of(1981, 1, 8));
		Assertions.assertThat(profile.getBloodType()).isEqualTo(BloodType.A);
		Assertions.assertThat(profile.getUpdates()).isEqualTo("元気です");
	}
}
