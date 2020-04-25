# Project Title

This is a demo project for spring boot + mysql.

## Getting Started

### Prerequisites

mysql, Intellij

### Installing


1. Install mysql
2. Load this project in Intellij
3. Configure your mysql connection in application.properties
4. right-click on StartNoteApplication and run it
5. Run a CURL command from the command line (see below) and check the results

## Running the tests

Open the project in Intellij, open the Maven Lifecycle, and run either compile or tests.

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
curl -X POST -H "Content-Type: application/json" -u "admin:password" -d "{\"userId\":1, \"title\":\"TITLE xyz\",\"note\":\"TEXT xyz\"}" http://localhost:8080/notes
```

Update a note
```
curl -X PUT -H "Content-Type: application/json" -u "admin:password" -d "{\"userId\":1, \"title\":\"NEW TITLE xyz\",\"note\":\"TEXT xyz\"}" http://localhost:8080/notes/2
```

Delete a note

```
curl -X DELETE -u "admin:password" -v localhost:8080/notes/1
```

## Deployment

Add additional notes about how to deploy this on a live system


## Contributing


## Versioning

Version 0.1

## Authors

* **Kenneth Kehl** - *Initial work* - [terrazoon](https://github.com/terrazoon)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

The earliest version of this application was based off sample code from www.mkyong.com