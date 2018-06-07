# batchSynSql
a tool can synchronize SQL script to multiple enviroment

### Enrivoment Config
you can mark down your env's config in **src/main/resource/database.properties**, the format of config is like:

    datasource.username.env1=test1
    datasource.password.env1=test1
    datasource.jdbcUrl.env1=jdbc:mysql://localhost:1433; DatabaseName=test1
    
the enviroment name can rename (like *env1* in the example), but make sure the same env use the same name.

### SQL Script
you can add your script in **src/main/resource/sql.sql**.
make sure multiple sql is splitted by delimiter [go] with new line