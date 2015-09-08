package com.rjuarez.core.manager;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rjuarez.core.model.User;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface UserManager extends GenericService<User,Long> {

    /**
     * Retrieves a user by userId. An exception is thrown if user not found
     *
     * @param userId
     *            the identifier for the user
     * @return User
     */
    User getUser(String userId);

    /**
     * Finds a user by their username.
     * 
     * @param username
     *            the user's username used to login
     * @return User a populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *             exception thrown when user not found
     */
    User getUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Saves a user's information.
     *
     * @param user
     *            the user's information
     * @return user the updated user object
     * @throws UserExistsException
     *             thrown when user already exists
     */
    User saveUser(User user) throws UserExistsException;

    /**
     * Builds a recovery password url by replacing placeholders with username
     * and generated recovery token. UrlTemplate should include two placeholders
     * '{username}' for username and '{token}' for the recovery token.
     * 
     * @param user
     *            The user which requires a recovery password.
     * @param urlTemplateurl
     *            template including two placeholders '{username}' and '{token}'
     * @return The recovery password.
     */
    String buildRecoveryPasswordUrl(User user, String urlTemplate);

    /**
     * Create a recovery token.
     * 
     * @param user
     *            The user which requires a recovery token.
     * @return The recovery token.
     */
    String generateRecoveryToken(User user);

    /**
     * Check if recovery token is valid.
     * 
     * @param username
     *            The username of the User validating a recovery token.
     * @param token
     *            The recovery token.
     * @return true or false
     */
    boolean isRecoveryTokenValid(String username, String token);

    /**
     * Check if recovery token is valid.
     * 
     * @param user
     *            The User validating a recovery token.
     * @param token
     *            The recovery token.
     * @return true or false
     */
    boolean isRecoveryTokenValid(User user, String token);

    /**
     * Sends a password recovery email to username.
     *
     * @param username
     *            The username the send a recovery mail to.
     * @param urlTemplate
     *            url template including two placeholders '{username}' and
     *            '{token}'
     */
    void sendPasswordRecoveryEmail(String username, String urlTemplate);

    /**
     * Update password.
     * 
     * @param username
     *            The username of the User updating the password.
     * @param currentPassword
     *            The current password for the given username.
     * @param recoveryToken
     *            The recovery token for the given username.
     * @param newPassword
     *            The new password for the given username.
     * @param applicationUrl
     *            The app url for the given username.
     * @return The user with updated password.
     * @throws UserExistsException
     *             Exception to be handled in the web layer.
     */
    User updatePassword(String username, String currentPassword, String recoveryToken, String newPassword, String applicationUrl)
            throws UserExistsException;

    User updateUser(User userToUpdate);

    /**
     * Get a User filteres list.
     * 
     * @param username
     *            Query to match the username of the searched users.
     * @param fullname
     *            Query to match the fullname of the searched users.
     * @param email
     *            Query to match the email of the searched users.
     * @return The filtered list.
     */
    List<User> getAllFiltered(String username, String fullname, String email);
}
