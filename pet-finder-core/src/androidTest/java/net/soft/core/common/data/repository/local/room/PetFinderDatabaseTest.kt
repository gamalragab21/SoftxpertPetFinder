package net.soft.core.common.data.repository.local.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import net.soft.core.common.domain.repository.local.room.PetFinderDao
import net.soft.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.soft.features.getPetByCategoryUC.data.models.entity.ColorsEntity
import net.soft.features.getPetByCategoryUC.data.models.entity.PhotoEntity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PetFinderDaoTest {

    private lateinit var petFinderDao: PetFinderDao
    private lateinit var petFinderDatabase: PetFinderDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        petFinderDatabase = Room.inMemoryDatabaseBuilder(
            context, PetFinderDatabase::class.java
        ).build()
        petFinderDao = petFinderDatabase.petFinderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        petFinderDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadData() = runBlocking {
        val animal = AnimalEntity(
            ColorsEntity(null, null, null),
            "1",
            "male",
            147,
            "Cat",
            PhotoEntity("mediumPath", "smallPath"),
            "",
            "Cairo,Egypt",
            "Small",
            "https://pet_finder"
        )

        // Insert data
        petFinderDao.insertAllAnimals(listOf(animal))

        // Read data
        val animals = petFinderDao.getAllAnimals(0)

        // Verify
        assertThat(animals.size, equalTo(1))
        assertThat(animals[0].id, equalTo(animal.id))
        assertThat(animals[0].name, equalTo(animal.name))
        assertThat(animals[0].type, equalTo(animal.type))
    }

    @Test
    @Throws(Exception::class)
    fun getAnimalsByCategory() = runBlocking {
        val animal1 = AnimalEntity(
            ColorsEntity(null, null, null),
            "1",
            "male",
            1,
            "Cat",
            PhotoEntity("mediumPath", "smallPath"),
            "Cat",
            "Cairo,Egypt",
            "Small",
            "https://pet_finder"
        )
        val animal2 = AnimalEntity(
            ColorsEntity(null, null, null),
            "1",
            "male",
            2,
            "Test Animal 2",
            PhotoEntity("mediumPath", "smallPath"),
            "Cat",
            "Cairo,Egypt",
            "Small",
            "https://pet_finder"
        )
        val animal3 = AnimalEntity(
            ColorsEntity(null, null, null),
            "1",
            "male",
            3,
            "Test Animal 3",
            PhotoEntity("mediumPath", "smallPath"),
            "Bird",
            "Cairo,Egypt",
            "Small",
            "https://pet_finder"
        )

        // Insert data
        petFinderDao.insertAllAnimals(listOf(animal1, animal2, animal3))

        // Read data by category
        val animalsByCategory = petFinderDao.getAnimalsByCategory("Cat", 0)

        // Verify
        assertThat(animalsByCategory.size, equalTo(2))
        assertThat(animalsByCategory[0].id, equalTo(animal1.id))
        assertThat(animalsByCategory[1].id, equalTo(animal2.id))
    }
}
