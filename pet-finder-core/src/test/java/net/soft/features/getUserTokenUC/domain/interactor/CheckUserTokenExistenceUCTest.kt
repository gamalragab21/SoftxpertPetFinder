package net.soft.features.getUserTokenUC.domain.interactor

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import net.soft.core.common.data.model.Resource
import net.soft.core.common.data.model.exception.LeonException
import net.soft.core.test.utils.runTestCaseBlock
import net.soft.features.getUserTokenUC.data.models.TokenRequest
import net.soft.features.getUserTokenUC.domain.models.Token
import net.soft.features.getUserTokenUC.domain.repository.IGetUserTokenRepository
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CheckUserTokenExistenceUCTest {

    @Mock
    private lateinit var repository: IGetUserTokenRepository

    @InjectMocks
    lateinit var checkUserTokenExistenceUC: CheckUserTokenExistenceUC

    private val validToken = Token("valid access Token", 3600, "Bearer", System.currentTimeMillis())
    private val inValidToken = Token("", 0, "", 0)

    private val validTokenRequest = TokenRequest("client_id", "secret_id")
    private val invalidTokenRequest = TokenRequest("", "secret_id")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `when user has a valid token, should emit user token from local`() = runTest {
        `when`(repository.getUserTokenFromLocal()).thenReturn(validToken)

        checkUserTokenExistenceUC.invoke().test {
            runTestCaseBlock {
                assertFalse((it as Resource.Success).model.isInInitialState())
                assertEquals(validToken, it.model)
            }
        }
    }

    @Test
    fun `when user has a non-valid token and valid token request, should emit user token from remote`() =
        runTest {
            `when`(repository.getUserTokenFromLocal()).thenReturn(inValidToken)
            `when`(repository.getUserTokenFromRemote(validTokenRequest)).thenReturn(validToken)

            checkUserTokenExistenceUC.invoke(validTokenRequest).test {
                runTestCaseBlock {
                    assertFalse((it as Resource.Success).model.isInInitialState())
                    assertEquals(validToken, it.model)
                }
            }
        }

    @Test
    fun `when user has a non-valid token and invalid token request, should emit Leon Exception from Request validation`() =
        runTest {
            `when`(repository.getUserTokenFromLocal()).thenReturn(inValidToken)

            checkUserTokenExistenceUC.invoke(invalidTokenRequest).test {
                runTestCaseBlock {
                    assertTrue((it as Resource.Failure).exception is LeonException.Local.RequestValidation)
                }
            }
        }

    @Test
    fun `when user has a non-valid token and token request is null, should emit Leon Exception from Request validation`() =
        runTest {
            `when`(repository.getUserTokenFromLocal()).thenReturn(inValidToken)

            checkUserTokenExistenceUC.invoke().test {
                runTestCaseBlock {
                    assertTrue((it as Resource.Failure).exception is LeonException.Local.RequestValidation)
                }
            }
        }

    @Test
    fun `when user has a non-valid token with valid token request and remote returns a non-valid token, use-case should not executeLocal`() =
        runTest {
            `when`(repository.getUserTokenFromLocal()).thenReturn(inValidToken)
            `when`(repository.getUserTokenFromRemote(validTokenRequest)).thenReturn(inValidToken)

            checkUserTokenExistenceUC.invoke(validTokenRequest).test {
                runTestCaseBlock {
                    assertTrue((it as Resource.Success).model.isInInitialState())
                }
            }

            verify(repository, times(0)).saveUserToken(inValidToken)
        }

    @Test
    fun `when user has a non-valid token with valid token request and remote returns a valid token, use-case should executeLocal`() =
        runTest {
            `when`(repository.getUserTokenFromLocal()).thenReturn(inValidToken)
            `when`(repository.getUserTokenFromRemote(validTokenRequest)).thenReturn(validToken)

            checkUserTokenExistenceUC.invoke(validTokenRequest).test {
                runTestCaseBlock {
                    assertFalse((it as Resource.Success).model.isInInitialState())
                }
            }

            verify(repository, times(1)).saveUserToken(validToken)
        }
}
