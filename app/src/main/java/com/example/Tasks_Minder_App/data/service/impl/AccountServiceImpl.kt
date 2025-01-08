package com.example.Tasks_Minder_App.data.service.impl

import com.example.Tasks_Minder_App.data.model.User
import com.example.Tasks_Minder_App.data.service.AccountService
import com.example.Tasks_Minder_App.utils.AccountCreationException
import com.example.Tasks_Minder_App.utils.AccountDeletionException
import com.example.Tasks_Minder_App.utils.Constants.LINK_ACCOUNT_TRACE
import com.example.Tasks_Minder_App.utils.LinkAccountException
import com.example.Tasks_Minder_App.utils.SignOutException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.AuthenticationException
import com.google.firebase.perf.trace
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of [AccountService] using Firebase Authentication to handle user account operations.
 *
 * @param firebaseAuth The [FirebaseAuth] instance for interacting with Firebase Authentication.
 */
class AccountServiceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AccountService {

    /**
     * A [Flow] representing the current authenticated user. Emits updates whenever the authentication state changes.
     */
    override val currentUser: Flow<User>
        get() =
            callbackFlow {
                val listener =
                    FirebaseAuth.AuthStateListener { auth ->
                        this.trySend(auth.currentUser?.let {
                            User(
                                userId = it.uid,
                                isAnonymous = it.isAnonymous
                            )
                        } ?: User())
                    }
                firebaseAuth.addAuthStateListener(listener)
                awaitClose { firebaseAuth.removeAuthStateListener(listener) }
            }

    /**
     * The unique identifier of the currently authenticated user, or an empty string if no user is signed in.
     */
    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    /**
     * Indicates whether there is an authenticated user.
     */
    override val isUserSignedIn: Boolean
        get() = firebaseAuth.currentUser != null

    /**
     * Authenticates a user using their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @throws AuthenticationException If authentication fails (e.g., invalid credentials).
     * @throws IOException If a network error occurs during the process.
     */
    override suspend fun authenticate(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            throw AuthenticationException("Authentication failed: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during authentication: ${e.message}", e)
        }
    }

    /**
     * Creates an anonymous account for the user.
     *
     * @throws AccountCreationException If the anonymous account creation fails.
     * @throws IOException If a network error occurs during the process.
     */
    override suspend fun createAnonymousAccount() {
        try {
            firebaseAuth.signInAnonymously().await()
        } catch (e: FirebaseAuthException) {
            throw AccountCreationException("Anonymous account creation failed: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException(
                "Network error occurred during anonymous account creation: ${e.message}", e
            )
        }
    }

    /**
     * Links an email and password to the current anonymous account.
     *
     * @param email The email address to link.
     * @param password The password to associate with the account.
     * @throws LinkAccountException If linking fails (e.g., email already in use).
     * @throws IOException If a network error occurs during the process.
     */
    override suspend fun linkAccount(email: String, password: String) {
        try {
            trace(LINK_ACCOUNT_TRACE) {
                val credential = EmailAuthProvider.getCredential(email, password)
                firebaseAuth.currentUser!!.linkWithCredential(credential).await()
            }
        } catch (e: FirebaseAuthException) {
            throw LinkAccountException("Linking account failed: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during account linking: ${e.message}", e)
        }
    }

    /**
     * Deletes the currently authenticated user's account.
     *
     * @throws AccountDeletionException If the account cannot be deleted.
     * @throws IOException If a network error occurs during the process.
     */
    override suspend fun deleteAccount() {
        try {
            firebaseAuth.currentUser!!.delete().await()
        } catch (e: FirebaseAuthException) {
            throw AccountDeletionException("Account deletion failed: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during account deletion: ${e.message}", e)
        }
    }

    /**
     * Signs out the currently authenticated user. If the user is anonymous, deletes the anonymous account
     * before signing out.
     * Automatically creates a new anonymous account after signing out.
     *
     * @throws SignOutException If the sign-out operation fails.
     */
    override suspend fun signOut() {
        try {
            if (firebaseAuth.currentUser!!.isAnonymous) {
                firebaseAuth.currentUser!!.delete()
            }
            firebaseAuth.signOut()
            // Sign the user back in anonymously.
            createAnonymousAccount()
        } catch (e: FirebaseAuthException) {
            throw SignOutException("Sign out failed: ${e.message}", e)
        } catch (e: IOException) {
            throw IOException("Network error occurred during sign out: ${e.message}", e)
        }
    }

    companion object {

    }
}