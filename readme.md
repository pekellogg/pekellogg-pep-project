# Social Media Blog API

## Background 

This project is a backend-only Java application for a social media app which leverages Javalin and JDBC. 

## Tables 

Tables are provided by the ConnectionUtil class which runs a provided SQL script within src/main/resources.

### Account
```
account_id integer primary key auto_increment,
username varchar(255),
password varchar(255)
```

### Message
```
message_id integer primary key auto_increment,
posted_by integer,
message_text varchar(255),
time_posted_epoch long,
foreign key (posted_by) references Account(account_id)
```

# Requirements

## 1: New User Registrations

As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. The request body contains a JSON representation of an Account.

The registration will be successful if and only if:
- the username is not blank
- the password is at least 4 characters long
- an Account with that username does not already exist

For successful new user registrations:
- the response body contains a JSON representation of the Account, including its account_id.
- the response status is the default 200 OK
- the new account is persisted to the database

For unsuccessful registrations:
- the response status is 400 (Client error)

## 2: User Logins

As a user, I should be able to verify my login on the endpoint POST localhost:8080/login. The request body contains a JSON representation of an Account.

The login will be successful if and only if:
- the username and password provided in the request body JSON match an existing account on the database.

For successful user logins:
- the response body contains a JSON representation of the account, including its account_id
- the response status is the default 200 OK

For unsuccessful logins:
- the response status is 401 (Unauthorized)


## 3: Creation of New Messages

As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages. The request body contains JSON representation of a message, which should be persisted to the database, but will not contain a message_id.

The creation of the message will be successful if and only if:
- the message_text is not blank
- the message_text is not over 255 characters
- posted_by refers to a real, existing user

For successful message creations:
- the response body contains a JSON representation of the message, including its message_id
- the response status is the default 200 OK
- the new message is persisted to the database

For unsuccessful creation of the messages:
- the response status is 400 (Client error)

## 4: Retrieve All Messages

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages. The response body contains a JSON representation of a list containing all messages retrieved from the database. It is expected for the list to simply be empty if there are no messages. The response status is always the default 200 OK.

## 5: Retrieve a Message By Its message_id

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}. The response body contains a JSON representation of the message identified by the message_id. It is expected for the response body to simply be empty if there is no such message. The response status is always the default 200 OK.

## 6: Delete a Message By Its message_id

As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.

- The deletion of an existing message should remove an existing message from the database. If the message existed, the response body should contain the now-deleted message. The response status is the default 200 OK.
- If the message did not exist, the response status should be 200, but the response body should be empty as DELETEs are idempotent, ie, multiple calls to the DELETE endpoint ought to respond with the same type of response.

## 7: Update a Message's message_text By Its message_id

As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}. The request body contains new message_text values to replace on the message having message_id. The request body cannot be guaranteed to contain any other information.

The update of a message should be successful if and only if:
- the message id already exists
- the new message_text is not blank
- the new message_text is not over 255 characters

For successful updates:
- the response body contains the full updated message (message_id, posted_by, message_text, time_posted_epoch)
- the response status is the default 200 OK
- the message existing on the database reflects the updated values for message_text

For unsuccessful updates (for any reason):
- the response status is 400 (Client error)

## 8: Retrieve All Messages Belonging to a User Having account_id

As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.

The response body contains a JSON representation of a list containing all messages posted by a particular user retrieved from the database. It is expected for the list to simply be empty if there are no messages. The response status is always the default 200 OK.