# Cluster docker

# tocken 1234

# Inserir o comando abaixo para baixar o repositório de assistente de instalação.

```sh
cd / && git clone https://github.com/diegozn/docker.git && cd /./docker/ && chmod +777 script-docker-mysql.sh && chmod +777 script.sh && chmod +777 script.sql && chmod +777 IngresseSofware.jar
```

# Remove todos os containers

```sh
docker rm $(docker ps -aq) -f
```
# Interage com o container via bash

```sh
docker exec -it contis bash
```
# Conecta com o mysql local

```sh
mysql -h localhost -uroot -proot ingressesoftware
```
# select dos registros

```sh
select * from totem;
```
```sh
select * from logs;
```
