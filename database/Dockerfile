FROM mysql:8
# doing so will actually preinstall any *.sql files in preload_sql_scripts folder
COPY ./preload_sql_scripts/* /docker-entrypoint-initdb.d/
