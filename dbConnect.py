import psycopg2
from psycopg2 import Error
from psycopg2.extensions import ISOLATION_LEVEL_AUTOCOMMIT

def main(argv):
    print(get_value(*argv[1:]))

if __name__ == "__main__":
    import sys
    main(sys.argv)

def connectToDB(*args):
  # Create DB
  create_db_query = "CREATE DATABASE modis;"
  try:
    connection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432")
    connection.set_isolation_level(ISOLATION_LEVEL_AUTOCOMMIT)
    cursor = connection.cursor()
    cursor.execute(create_db_query)
  except (Exception, psycopg2.DatabaseError) as error :
    print ("Error while creating PostgreSQL DB:", error)
  # Create table
  try:
    connection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", database = "modis")
    create_table_query = '''CREATE TABLE modis_data
            (ID                BIGSERIAL     PRIMARY KEY     NOT NULL,
            FILENAME           TEXT    NOT NULL,
            FILEDATE           TEXT    NOT NULL,
            LATITUDE           TEXT    NOT NULL,
            LONGITUDE          TEXT    NOT NULL,
            PIXEL_VALUE        TEXT    NOT NULL,
            PIXEL_BINARY_VALUE TEXT    NOT NULL,
            CM_FLAG            TEXT    NOT NULL,
            CM_VALUE           TEXT    NOT NULL); '''
    cursor.execute(create_table_query)
    connection.commit()
    print("Table modis_data created successfully in PostgreSQL ")
  except (Exception, psycopg2.DatabaseError) as error :
      print ("Error while creating PostgreSQL table:", error)
  # finally:
  #     #closing database connection.
  #         if(connection):
  #             cursor.close()
  #             connection.close()
  #             print("PostgreSQL connection is closed")

  # try:
  #   connection = psycopg2.connect(user = "postgres", password = "postgres", host = "127.0.0.1", port = "5432", database = "modis")
  #   query =  'INSERT INTO modis_data (filename, filedate, latitude, longitude, pixel_value, pixel_binary_value, cm_flag, cm_value) VALUES (%s, %s, %s, %s, %s, %s, %s, %s);'
  #   data = ("A124431", "2017001", "45,233424", "67,324252", "29", "01010011", "01", "11")
  #   cursor = connection.cursor()
  #   cursor.execute(query, data)
  #   connection.commit()
  # except (Exception, psycopg2.DatabaseError) as error :
  #   print(error)