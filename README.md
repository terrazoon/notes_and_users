# Project Title

Notes and Users

## Getting Started

This is a demo project to show a Spring Boot REST API with http authentication.  For demo purposes,
there are two defined users ('user' and 'admin').  Each one is authorized to create, read, update,
and delete their own notes -- but not the notes of the other user.

### Prerequisites

mysql, IntelliJ

### Installing

1. Install mysql
2. Load this project in Intellij
3. Update application.properties
    a. set your mysql username and password
    b. make sure the connection settings are correct for your mysql instance
4. Manually create the database 'notes_demo' by running the query "CREATE DATABBASE notes_demo"
5. right-click on StartNoteApplication and run it
6. Run a CURL command from the command line (see below) and check the results

## Running the tests

Open the project in IntelliJ, open the Maven Lifecycle, and run tests.

### Sample CURL Commands to test CRUD Functionality

Get a list of all notes in the database
```
curl -u "user:password" -v localhost:8080/notes
```

Get a specific note (by id)
```
curl -u "user:password" -v localhost:8080/notes/1
```

Add a new note

```
curl -X POST -H "Content-Type: application/json" -u "user:password" -d "{\"userId\":1, \"title\":\"TITLE xyz\",\"note\":\"TEXT xyz\"}" http://localhost:8080/notes
```

Update a note
```
curl -X PUT -H "Content-Type: application/json" -u "user:password" -d "{\"userId\":1, \"title\":\"NEW TITLE xyz\",\"note\":\"TEXT xyz\"}" http://localhost:8080/notes/2
```

Delete a note

```
curl -X DELETE -u "user:password" -v localhost:8080/notes/1
```

## Versioning

Version 0.1

## Authors

* **Kenneth Kehl** - *Initial work* - [terrazoon](https://github.com/terrazoon)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

The earliest version of this application was based off sample code from www.mkyong.com, which is licensed under the MIT License.  See the [LICENSE.old](LICENSE.old) file for details