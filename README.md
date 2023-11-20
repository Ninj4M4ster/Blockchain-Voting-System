# Blockchain-Voting-System

## Dependencies

### Postgresql
This web application uses postgresql database to run. 
Before running the server for the first time, you will need to install it
and create databases.

To install postgresql, run

```bash
sudo apt-get install postgresql
```

Then, you will need to create database and populate it. In order
to do this, run following command from project root directory:

```bash
sudo -i -u postgres
psql -U postgres postgres -f /path/to/Blockchain-Voting-System/scripts/create_database.sql
```

After this, everything should be running and ready.