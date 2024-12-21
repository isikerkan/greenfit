Start local db first time

```bash
docker run --name greenfit-postgres -e POSTGRES_PASSWORD=Sample123 -d -p 5432:5432 postgres
```

Any subsequent times
```bash
docker start greenfit-postgres
```


Build image
```bash
./mvnw spring-boot:build-image
```

Run locally

```bash
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker --net greenfit greenfit:0.0.1-SNAPSHOT
```

# Deployment
Locally
```bash
./mvnw spring-boot:build-image
docker tag greenfit:1.0.0-SNAPSHOT docker.io/banshay/greenfit:latest
docker push docker.io/banshay/greenfit:latest
```

On the box

Stop old container 
```bash
docker ps
docker stop <container name>

# not needed with --rm flag
#docker container rm <container name>
```
Start new one
```bash
docker pull banshay/greenfit
docker run --env-file /var/opt/.env --rm -d -p 8080:8080 banshay/greenfit
```
