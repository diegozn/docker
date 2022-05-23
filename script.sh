docker build -t ingresse-image .
docker run -d --name ingresse -p 3306:3306 ingresse-image
docker exec -it ingresse bash

