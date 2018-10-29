import psycopg2
from psycopg2 import Error

try:
    connection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", database = "main")
    cursor = connection.cursor()
    create_table_query = '''CREATE TABLE modis_data
          (ID INT PRIMARY KEY     NOT NULL,
          FILENAME           TEXT    NOT NULL,
          FILEDATE           TEXT    NOT NULL,
          LATITUDE           TEXT    NOT NULL,
          LONGITUDE           TEXT    NOT NULL,
          PIXEL_VALUE           TEXT    NOT NULL,
          PIXEL_BINARY_VALUE           TEXT    NOT NULL,
          CM_FLAG           TEXT    NOT NULL,
          CM_VALUE         TEXT    NOT NULL); '''
    cursor.execute(create_table_query)
    connection.commit()
    print("Table modis_data created successfully in PostgreSQL ")
except (Exception, psycopg2.DatabaseError) as error :
    print ("Error while creating PostgreSQL table", error)
finally:
    #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")