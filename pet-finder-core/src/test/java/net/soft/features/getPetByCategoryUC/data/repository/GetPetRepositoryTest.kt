package net.soft.features.getPetByCategoryUC.data.repository

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.any
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import net.soft.core.common.data.model.Resource
import net.soft.core.common.data.model.exception.LeonException
import net.soft.core.test.utils.runTestCaseBlock
import net.soft.features.getPetByCategoryUC.data.models.CategoryPetRequest
import net.soft.features.getPetByCategoryUC.data.models.dto.AddressDto
import net.soft.features.getPetByCategoryUC.data.models.dto.AnimalDto
import net.soft.features.getPetByCategoryUC.data.models.dto.ColorsDto
import net.soft.features.getPetByCategoryUC.data.models.dto.ContactDto
import net.soft.features.getPetByCategoryUC.data.models.dto.PaginationDto
import net.soft.features.getPetByCategoryUC.data.models.dto.PetResponseDto
import net.soft.features.getPetByCategoryUC.data.models.dto.PhotoDto
import net.soft.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.soft.features.getPetByCategoryUC.data.models.entity.ColorsEntity
import net.soft.features.getPetByCategoryUC.data.models.entity.PhotoEntity
import net.soft.features.getPetByCategoryUC.domain.interactor.GetPetByCategoryUC
import net.soft.features.getPetByCategoryUC.domain.repository.IGetPetRepository
import net.soft.features.getPetByCategoryUC.domain.repository.local.IGetPetByCategoryLocalDs
import net.soft.features.getPetByCategoryUC.domain.repository.remote.IGetPetByCategoryRemoteDs
import net.soft.features.getPetTypes.domain.enums.PetType
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetPetByCategoryUCTest {

    private val petType = PetType.CAT
    private val currentPage = 1
    private val validCategoryPetRequest = CategoryPetRequest(petType, currentPage)
    private val invalidCategoryPetRequest = CategoryPetRequest(PetType.ALL.apply {
        type = ""
    }, 0)

    // Mocks
    @Mock
    private lateinit var localDs: IGetPetByCategoryLocalDs

    @Mock
    private lateinit var remoteDs: IGetPetByCategoryRemoteDs

    // Class under test
    private lateinit var repository: IGetPetRepository
    private lateinit var getPetByCategoryUC: GetPetByCategoryUC

    private val validAnimalsEntity = listOf(
        AnimalEntity(
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
    )
    private val animalDto = AnimalDto(
        "1",
        ColorsDto(null, null, null),
        "male",
        147,
        "Cat",
        listOf(PhotoDto("mediumPath", "smallPath")),
        "",
        ContactDto(AddressDto(null, null, "Cairo", "Eg", "20", "")),
        "Small",
        "https://pet_finder"
    )

    @Before
    fun setUp() {
        // Initialize mocks and the class under test
        MockitoAnnotations.openMocks(this)
        repository = GetPetRepository(localDs, remoteDs)
        getPetByCategoryUC = GetPetByCategoryUC(repository)
    }

    @Test
    fun `when exists data in  local DS with valid request, should emit pets from local`() =
        runTest {
            // Given
            whenever(localDs.getAnimals(petType, currentPage)).doReturn(validAnimalsEntity)
            whenever(localDs.getTotalItems()).doReturn(1)

            // When
            getPetByCategoryUC.invoke(validCategoryPetRequest).test {
                runTestCaseBlock {
                    // Then
                    assertFalse((it as Resource.Success).model.animals.isEmpty())
                    assertEquals(1, it.model.pagination.totalPages)
                    assertEquals(validAnimalsEntity.first().id, it.model.animals.first().id)
                }
            }

            verify(localDs, Mockito.timeout(1)).getAnimals(petType, currentPage)
            verify(localDs, Mockito.timeout(1)).getTotalItems()
        }

    @Test
    fun `when executing local DS with invalid request, should throw RequestValidation exception`() =
        runTest {
            // When
            getPetByCategoryUC.invoke(invalidCategoryPetRequest).test {
                runTestCaseBlock {
                    assertTrue((it as Resource.Failure).exception is LeonException.Local.RequestValidation)
                }
            }
        }

    @Test
    fun `when performing remote operation with empty animals list, should return true`() = runTest {
        // Given
        val pet = PetResponseDto(emptyList(), PaginationDto(currentPage, 0))

        whenever(localDs.getAnimals(petType, currentPage)).doReturn(emptyList())
        whenever(remoteDs.getPetsByCategoryName(validCategoryPetRequest.remoteMap)).doReturn(pet)
        whenever(localDs.getTotalItems()).doReturn(0)

        // When
        getPetByCategoryUC.invoke(validCategoryPetRequest).test {
            runTestCaseBlock {
                assertTrue((it as Resource.Success).model.animals.isEmpty())
            }
        }

        // Then
        verify(localDs, Mockito.times(0)).insertAnimals(any())
    }

    @Test
    fun `when executing remote DS, should emit pets from remote`() = runTest {
        // Given
        val pet = PetResponseDto(listOf(animalDto), PaginationDto(currentPage, 1))

        whenever(localDs.getAnimals(petType, currentPage)).doReturn(emptyList())
        whenever(remoteDs.getPetsByCategoryName(validCategoryPetRequest.remoteMap)).doReturn(pet)
        whenever(localDs.getTotalItems()).doReturn(0)

        // When
        getPetByCategoryUC.invoke(validCategoryPetRequest).test {
            runTestCaseBlock {
                assertTrue((it as Resource.Success).model.animals.size == 1)
            }
        }
    }

    @Test
    fun `when performing local operation with non-empty animals list, should save animals to local`() =
        runTest {
            // Given
            val pet = PetResponseDto(listOf(animalDto), PaginationDto(currentPage, 1))

            whenever(localDs.getAnimals(petType, currentPage)).doReturn(emptyList())
            whenever(remoteDs.getPetsByCategoryName(validCategoryPetRequest.remoteMap)).doReturn(
                pet
            )
            whenever(localDs.getTotalItems()).doReturn(0)

            // When
            getPetByCategoryUC.invoke(validCategoryPetRequest).test {
                runTestCaseBlock {
                    assertTrue((it as Resource.Success).model.animals.isNotEmpty())
                }
            }

            // Then
            verify(localDs, Mockito.times(1)).insertAnimals(any())
        }
}
