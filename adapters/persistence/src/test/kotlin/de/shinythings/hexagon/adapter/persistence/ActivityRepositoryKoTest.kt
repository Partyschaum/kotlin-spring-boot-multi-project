package de.shinythings.hexagon.adapter.persistence

import de.shinythings.hexagon.adapter.persistence.activity.ActivityRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime
import javax.persistence.EntityManager

@DataJpaTest
class ActivityRepositoryKoTest : AnnotationSpec() {

    override fun isolationMode() = IsolationMode.SingleInstance

    @Autowired
    private lateinit var activityRepository: ActivityRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    @Sql("/de.shinythings.hexagon.adapter.persistence/ActivityRepositoryTest.sql")
    fun `test this`() {
        val depositBalance = activityRepository.depositBalanceUntil(
                accountId = 1,
                until = LocalDateTime.now()
        )

        depositBalance shouldBe 2000
    }
}
