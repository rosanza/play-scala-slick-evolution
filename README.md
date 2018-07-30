# play-scala-slick-evolution

This is mini-project using Play framework, Scala Language, Slick Data Layer, and Evolution to handle database migration.

    Database Setting (PostgreSQL)
    
    Host: localhost
    Database name: postgres
    User: postgres
    Password: postgres

To Run project, start up Play:
                
        sbt run
        
Then open browser to `http://localhost:9000`, and you will see a form to register user to the database.
Go to `/users` page to see list of registered users in json format.