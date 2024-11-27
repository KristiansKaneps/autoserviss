# autoserviss

Projekts ir radīts balstoties uz Quarkus ietvaru.

Ir nepieciešama Java versija >=21

Ir nepieciešams lejupielādēt Linux versijas Oracle 21c datubāzi (LINUX.X64_213000_db_home.zip)
no: https://www.oracle.com/database/technologies/oracle21c-linux-downloads.html. Šo arhīvu nevajag atspiest, un to ir
nepieciešams pārkopēt vai pārvietot uz: `./docker/oracle/LINUX.X64_213000_db_home.zip`.

### Lai uzbūvētu projektu:

Nepieciešams uzbūvēt Docker servisus (datubāzi u.c.):

```shell
docker compose build
```

<br>

### Lai startētu projektu izstrādes režīmā:

1. Startē Docker servisus (datubāzi u.c.):
    ```shell
    docker compose up
    ```
   vai (ja Docker servisi netika uzbūvēti):
    ```shell
    docker compose up --build
    ```
2. Startē Quarkus:
    ```shell
    ./gradlew quarkusDev
    ```
3. Projekts ir palaists: http://localhost:8080/
   <br>
