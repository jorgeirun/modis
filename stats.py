import os, fnmatch, time,sys, subprocess, json
import psycopg2
from psycopg2 import Error
from psycopg2.extras import RealDictCursor

user = "modis"
password = "modis"
dbname = "modis"

connection = psycopg2.connect(user = user, password = password, host = "127.0.0.1", port = "5432", dbname = dbname)
cursor = connection.cursor()
# query = "SELECT COUNT(t.*) from modis_data01 as t join gis_paraguay as p on ST_WITHIN(t.geom, p.geom) where datevalue = 'A2017001'"
# cursor.execute(query)
# total = cursor.fetchone()[0]
# print("Total points in PY: "+str(total))
total = 402435
print("Points with Clouds per day:")
for x in range(1, 366):
	query = "SELECT COUNT(t.*) from modis_data_py_clouds as t join gis_paraguay as p on ST_WITHIN(t.geom, p.geom) where datenumber = "+str(x)+" and cmflag = '1' and cmconfvalue = '00'"
	cursor.execute(query)
	result = cursor.fetchone()
	dayCloudCount = result[0]
	percent = (int(dayCloudCount)*100)/total
	percent = '%.2f'%(percent)
	print(str(x)+" - "+str(dayCloudCount)+" - "+str(percent)+"%")
	query =  "INSERT INTO stats_day (datenumber, points_with_cloud, percentage) VALUES ("+str(x)+","+str(dayCloudCount)+", "+str(percent)+");"
	cursor.execute(query)
connection.commit()
connection.close()