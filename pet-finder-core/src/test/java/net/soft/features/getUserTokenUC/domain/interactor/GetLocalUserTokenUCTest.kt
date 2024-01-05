package net.soft.features.getUserTokenUC.domain.interactor

import app.cash.turbine.test
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import net.soft.core.common.data.model.Resource
import net.soft.core.test.utils.runTestCaseBlock
import net.soft.features.getUserTokenUC.data.models.entity.TokenEntity
import net.soft.features.getUserTokenUC.domain.repository.local.IGetUserTokenLocalDs
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Testcases:
 * when no token in local , use-cases should return token in initial state
 * when exist valid token in local , use-cases should return valid token
 */
internal class GetLocalUserTokenUCTest {
    @Mock
    lateinit var localDs: IGetUserTokenLocalDs

    @InjectMocks
    lateinit var getLocalUserTokenUC: GetLocalUserTokenUC

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `when no exist token in local , use-cases should return token in initial state`() =
        runTest {
            `when`(localDs.getUserToken()).thenReturn(TokenEntity("", 0, "", 0))
            getLocalUserTokenUC.invoke().test {
                runTestCaseBlock {
                    assertTrue((it as Resource.Success).model.isInInitialState())
                }
            }
        }

    @Test
    fun `when exist valid token in local , use-cases should return valid token`() =
        runTest {
            val validToken =
                TokenEntity("valid access token", 3600, "Bearer", System.currentTimeMillis())
            `when`(localDs.getUserToken()).thenReturn(validToken)
            getLocalUserTokenUC.invoke().test {
                runTestCaseBlock {
                    assertFalse((it as Resource.Success).model.isInInitialState())
                }
            }
        }
}